package com.app.businessBridge.domain.position.controller;

import com.app.businessBridge.domain.position.service.GradeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/grades")
public class ApiV1GradeController {
    private final GradeService gradeService;
}
