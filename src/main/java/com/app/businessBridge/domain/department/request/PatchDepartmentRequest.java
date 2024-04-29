package com.app.businessBridge.domain.department.request;

import lombok.Data;

@Data
public class PatchDepartmentRequest {
    private Integer departmentCode;
    private String departmentName;
}
