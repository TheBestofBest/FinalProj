package com.app.businessBridge.domain.grade.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class PostGradeRequest {

    // 직급 코드
    @NotBlank
    private Integer gradeCode;

    // 직급명
    @NotBlank
    private String gradeName;
}
