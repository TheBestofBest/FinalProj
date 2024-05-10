package com.app.businessBridge.domain.rebate.controller;


import com.app.businessBridge.domain.member.entity.Member;
import com.app.businessBridge.domain.rebate.dto.RebateDto;
import com.app.businessBridge.domain.rebate.entity.Rebate;
import com.app.businessBridge.domain.rebate.request.RebateRequest;
import com.app.businessBridge.domain.rebate.response.RebateResponse;
import com.app.businessBridge.domain.rebate.service.RebateService;
import com.app.businessBridge.global.RsData.RsData;
import com.app.businessBridge.global.holidayapi.ApiExplorer;
import com.app.businessBridge.global.request.Request;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/rebates")
public class ApiV1RebateController {

    private final RebateService rebateService;

    private final Request rq;

    private final ApiExplorer apiExplorer;

    @GetMapping("/{year}/{month}")
    public String testGetHoilday(@PathVariable(value = "year") String year,
                                 @PathVariable(value = "month") String month) throws IOException {

        System.out.println(apiExplorer.getHoilDay(year, month));

        return "휴일 api";
    }

    @GetMapping("/all/{year}/{month}")
    public String testGetAllDay(@PathVariable(value = "year") String year,
                                @PathVariable(value = "month") String month) throws IOException {

        System.out.println(apiExplorer.getAllDay(year, month));

        return "모든 날짜 api";
    }

    @GetMapping("")
    public void getRebates() {
        Member member = rq.getMember();
    }

    @GetMapping("/{id}")
    public void getRebate() {
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
