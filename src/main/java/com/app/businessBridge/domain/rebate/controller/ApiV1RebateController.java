package com.app.businessBridge.domain.rebate.controller;


import com.app.businessBridge.domain.member.entity.Member;
import com.app.businessBridge.domain.rebate.service.RebateService;
import com.app.businessBridge.global.request.Request;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/rebates")
public class ApiV1RebateController {

    private final RebateService rebateService;

    private final Request rq;


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
