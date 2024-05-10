package com.app.businessBridge.domain.statistics.controller;


import com.app.businessBridge.domain.statistics.service.StatisticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/statistics")
public class ApiV1StatisticsController {

    private final StatisticsService statisticsService;

    @GetMapping("")
    public void getStatistics() {

    }
}
