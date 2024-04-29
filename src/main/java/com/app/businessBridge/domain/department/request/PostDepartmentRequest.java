package com.app.businessBridge.domain.department.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PostDepartmentRequest {

    @NotNull
    private Integer departmentCode;

    @NotBlank
    private String departmentName;
}
