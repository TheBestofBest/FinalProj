package com.app.businessBridge.domain.rebate.controller;


import com.app.businessBridge.domain.member.Service.MemberService;
import com.app.businessBridge.domain.member.entity.Member;
import com.app.businessBridge.domain.rebate.dto.RebateDto;
import com.app.businessBridge.domain.rebate.dto.RebatesDto;
import com.app.businessBridge.domain.rebate.entity.Rebate;
import com.app.businessBridge.domain.rebate.request.RbSearchRequest;
import com.app.businessBridge.domain.rebate.request.RebateRequest;
import com.app.businessBridge.domain.rebate.request.SaveRequest;
import com.app.businessBridge.domain.rebate.response.RebateResponse;
import com.app.businessBridge.domain.rebate.response.RebatesResponse;
import com.app.businessBridge.domain.rebate.service.RebateService;
import com.app.businessBridge.global.RsData.RsCode;
import com.app.businessBridge.global.RsData.RsData;
import com.app.businessBridge.global.request.Request;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/rebates")
public class ApiV1RebateController {

    private final RebateService rebateService;

    private final MemberService memberService;

    private final Request rq;

    @GetMapping("")
    public RsData<RebatesResponse> getRebates() throws IOException {

        Member member = rq.getMember();

        if (member.getGrade().getCode() != 1000 || !member.getGrade().getName().equals("슈퍼관리자")) {
            return RsData.of(
                    RsCode.F_02,
                    "접근 권한이 없습니다.",
                    null
            );
        }

        LocalDate currentDate = LocalDate.now();
        String year = String.valueOf(currentDate.getYear());
        String month = String.valueOf(currentDate.getMonthValue());

        List<Rebate> rebateList = this.rebateService.findByYearAndMonth(year,month);

        List<RebatesDto> rebates = new ArrayList<>();
        Long totalSum = 0L;

        for (int i = 0; i < rebateList.size(); i++) {
            rebates.add(new RebatesDto(rebateList.get(i)));
            totalSum += rebateList.get(i).getSalary();
        }

        return RsData.of(
                RsCode.S_01,
                year + "년 " + month + "월 " + "정산내역 불러오기 성공",
                new RebatesResponse(rebates, totalSum)
        );
    }

    @PostMapping("/search")
    public RsData<RebatesResponse> getRebatesBySearch(@RequestBody RbSearchRequest rbSearchRequest) {

        Member member = rq.getMember();

        if (member.getGrade().getCode() != 1000 || !member.getGrade().getName().equals("슈퍼관리자")) {
            return RsData.of(
                    RsCode.F_02,
                    "접근 권한이 없습니다.",
                    null
            );
        }

        String month = rbSearchRequest.getMonth();
        List<Rebate> rebateList = new ArrayList<>();

        Member searchedMember = this.memberService.findByName(rbSearchRequest.getUserInfo()).getData();

        if(month.startsWith("0")) month = month.replace("0","");

        if(searchedMember==null
                && rbSearchRequest.getDept().isEmpty()
                && !rbSearchRequest.getYear().isEmpty()
                && !rbSearchRequest.getMonth().isEmpty()) {
            rebateList = this.rebateService.findByYearAndMonth(rbSearchRequest.getYear(), month);
        } else if (rbSearchRequest.getMonth().isEmpty()
                && rbSearchRequest.getDept().isEmpty()
                && !rbSearchRequest.getYear().isEmpty()
                && searchedMember!=null) {
            rebateList = this.rebateService.findByYearAndMember(rbSearchRequest.getYear(), searchedMember.getId());
        } else if (!rbSearchRequest.getDept().isEmpty()
                && !rbSearchRequest.getMonth().isEmpty()
                && searchedMember == null) {
            rebateList = this.rebateService.findByDeptAndMonth(rbSearchRequest.getDept(), rbSearchRequest.getMonth());
        } else if(searchedMember!=null
                && !rbSearchRequest.getYear().isEmpty()
                && !rbSearchRequest.getMonth().isEmpty()) {
            rebateList = this.rebateService.findBySearch(rbSearchRequest.getYear(), month, searchedMember.getId());
        }

        List<RebatesDto> rebates = new ArrayList<>();

        Long totalSum = 0L;

        for (int i = 0; i < rebateList.size(); i++) {
            rebates.add(new RebatesDto(rebateList.get(i)));
            totalSum += rebateList.get(i).getSalary();
        }

        return RsData.of(
                RsCode.S_01,
                "정산내역 불러오기 성공",
                new RebatesResponse(rebates, totalSum)
        );
    }

    @GetMapping("/{id}")
    public RsData<RebateResponse> getRebate(@PathVariable(value = "id") Long id) {

        Member member = rq.getMember();

        // 자신의 급여명세서 목록만 볼 수 있거나 관리자 일때만 볼 수 있도록
        if (!member.getId().equals(this.rebateService.findById(id).getData().getMember().getId()) && (member.getGrade().getCode() != 1000 || !member.getGrade().getName().equals("슈퍼관리자"))) {
            return RsData.of(
                    RsCode.F_02,
                    "접근 권한이 없습니다.",
                    null
            );
        }

        RsData<Rebate> rsData = this.rebateService.findById(id);

        return RsData.of(rsData.getRsCode(), rsData.getMsg(), new RebateResponse(new RebateDto(rsData.getData())));

    }

    @GetMapping("/me")
    public RsData<RebatesResponse> getMyRebates() {

        Member member = rq.getMember();

        // 로그인한 유저의 급여명세서만 불러오기
        List<Rebate> myRebates = this.rebateService.findMyRebates(member.getId());

        List<RebatesDto> rebates = new ArrayList<>();

        for (int i = 0; i < myRebates.size(); i++) {
            rebates.add(new RebatesDto(myRebates.get(i)));
        }

        return RsData.of(
                RsCode.S_01,
                "불러오기 성공",
                new RebatesResponse(rebates, 0L)
        );
    }

    @PatchMapping("/{id}")
    public void modifyRebate(@Valid @RequestBody RebateRequest.PatchRequest patchRequest) {

        String bonus = patchRequest.getBonus().replace(",","");

        Rebate modifyRebate = this.rebateService.findById(Long.valueOf(patchRequest.getRebateId())).getData();

        this.rebateService.modifyBonus(modifyRebate, bonus);

    }

    @PostMapping("")
    public RsData<RebateResponse> createRebate(@Valid @RequestBody RebateRequest rebateRequest) throws IOException {
        Member member = rq.getMember();

        RsData<Rebate> rsData = this.rebateService.createRebate(member, rebateRequest.getYear(), rebateRequest.getMonth());

        return RsData.of(rsData.getRsCode(), rsData.getMsg(), new RebateResponse(new RebateDto(rsData.getData())));
    }

    @PostMapping("/save")
    public RsData saveRebates(@Valid @RequestBody List<SaveRequest> saveRequests) {

        return this.rebateService.saveRebates(saveRequests);

    }

    @DeleteMapping("/{id}")
    public void deleteRebate() {


    }

}