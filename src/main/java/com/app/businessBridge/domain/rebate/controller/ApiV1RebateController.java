package com.app.businessBridge.domain.rebate.controller;


import com.app.businessBridge.domain.rebate.service.RebateService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/rebates")
public class ApiV1RebateController {

    private final RebateService rebateService;

    @GetMapping("")
    public void getRebates() {

    }

    @GetMapping("{id}")
    public void getRebate() {

    }

    @PostMapping("")
    public void createRebate() {

    }

    @DeleteMapping("{id}")
    public void deleteRebate() {

    }

}
