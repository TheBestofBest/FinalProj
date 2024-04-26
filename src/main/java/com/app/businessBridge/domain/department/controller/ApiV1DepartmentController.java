package com.app.businessBridge.domain.department.controller;

import com.app.businessBridge.domain.department.service.DepartmentService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/departments")
public class ApiV1DepartmentController {
    private final DepartmentService departmentService;
}
