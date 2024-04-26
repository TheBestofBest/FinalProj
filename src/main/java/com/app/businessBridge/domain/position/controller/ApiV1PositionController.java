package com.app.businessBridge.domain.position.controller;

import com.app.businessBridge.domain.position.service.PositionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/positions")
public class ApiV1PositionController {
    private final PositionService positionService;
}
