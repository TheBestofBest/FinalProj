package com.app.businessBridge.domain.schedule.controller;

import com.app.businessBridge.domain.schedule.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/schedule")
@RequiredArgsConstructor
public class ApiV1ScheduleController {

    private final ScheduleService scheduleService;

}
