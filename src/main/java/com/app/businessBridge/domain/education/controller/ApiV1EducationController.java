package com.app.businessBridge.domain.education.controller;


import com.app.businessBridge.domain.education.service.EducationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/educations")
public class ApiV1EducationController {

    private final EducationService educationService;

}
