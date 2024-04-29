package com.app.businessBridge.domain.grade.request;

import lombok.Data;

@Data
public class PatchGradeRequest {
    private Integer gradeCode;
    private String gradeName;
}
