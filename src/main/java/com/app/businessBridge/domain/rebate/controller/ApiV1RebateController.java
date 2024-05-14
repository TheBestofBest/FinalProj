package com.app.businessBridge.domain.rebate.controller;


import com.app.businessBridge.domain.member.Service.MemberService;
import com.app.businessBridge.domain.member.entity.Member;
import com.app.businessBridge.domain.rebate.dto.RebateDto;
import com.app.businessBridge.domain.rebate.dto.RebatesDto;
import com.app.businessBridge.domain.rebate.entity.Rebate;
import com.app.businessBridge.domain.rebate.request.RebateRequest;
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

        if (member.getGrade().getCode() != 1 || !member.getGrade().getName().equals("슈퍼관리자")) {
            return RsData.of(
                    RsCode.F_02,
                    "접근 권한이 없습니다.",
                    null
            );
        }

        List<RebatesDto> rebates = new ArrayList<>();

        for (int i = 0; i < this.rebateService.findAll().size(); i++) {
            rebates.add(new RebatesDto(this.rebateService.findAll().get(i)));
        }

        return RsData.of(
                RsCode.S_01,
                "불러오기 성공",
                new RebatesResponse(rebates)
        );
    }

    @GetMapping("/{id}")
    public RsData<RebateResponse> getRebate(@PathVariable(value = "id") Long id) {

        Member member = rq.getMember();

        // 자신의 급여명세서 목록만 볼 수 있거나 관리자 일때만 볼 수 있도록
        if (!member.getId().equals(this.rebateService.findById(id).getData().getMember().getId()) && (member.getGrade().getCode() != 1 || !member.getGrade().getName().equals("슈퍼관리자"))) {
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
                new RebatesResponse(rebates)
        );
    }

    @PostMapping("")
    public RsData<RebateResponse> createRebate(@Valid @RequestBody RebateRequest rebateRequest) throws IOException {
        Member member = rq.getMember();

        RsData<Rebate> rsData = this.rebateService.createRebate(member, rebateRequest.getYear(), rebateRequest.getMonth());

        return RsData.of(rsData.getRsCode(), rsData.getMsg(), new RebateResponse(new RebateDto(rsData.getData())));
    }

    @DeleteMapping("/{id}")
    public void deleteRebate() {

    }

}