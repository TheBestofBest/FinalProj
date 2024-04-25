package com.app.businessBridge.domain.confirm.controller;

import com.app.businessBridge.domain.confirm.service.ConfirmService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/confirms")
public class ApiV1ConfirmController {
    private final ConfirmService confirmService;
}
