package com.app.businessBridge.domain.workingstate.controller;

import com.app.businessBridge.domain.rebate.service.RebateService;
import com.app.businessBridge.domain.workingstate.service.WorkingStateService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/workingstates")
public class ApiV1WorkingStateController {

    private final WorkingStateService workingStateService;

}
