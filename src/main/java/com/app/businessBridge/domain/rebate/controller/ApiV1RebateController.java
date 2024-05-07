package com.app.businessBridge.domain.rebate.controller;


import com.app.businessBridge.domain.member.entity.Member;
import com.app.businessBridge.domain.rebate.service.RebateService;
import com.app.businessBridge.global.RsData.RsData;
import com.app.businessBridge.global.hoildayapi.ApiExplorer;
import com.app.businessBridge.global.hoildayapi.dto.HoliDayDto;
import com.app.businessBridge.global.request.Request;
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
    public RsData<HoliDayDto> testGetHoilday(@PathVariable(value = "year") String year,
                                             @PathVariable(value = "month") String month) throws IOException {

        return apiExplorer.getHoilDay(year, month);
    }

    @GetMapping("/all/{year}/{month}")
    public String testGetAllDay(@PathVariable(value = "year") String year,
                                @PathVariable(value = "month") String month) throws IOException {

        return apiExplorer.getAllDay(year, month);
    }

    @GetMapping("")
    public void getRebates() {
        Member member = rq.getMember();
    }

    @GetMapping("/{id}")
    public void getRebate() {

    }

    @PostMapping("")
    public void createRebate() {

    }

    @DeleteMapping("/{id}")
    public void deleteRebate() {

    }

}
