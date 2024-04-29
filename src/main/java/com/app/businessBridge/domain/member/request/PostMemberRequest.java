package com.app.businessBridge.domain.member.request;

import com.app.businessBridge.domain.department.entity.Department;
import com.app.businessBridge.domain.grade.entity.Grade;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PostMemberRequest {
    @NotNull
    private Department department;
    @NotNull
    private Grade grade;
    @NotBlank
    private String username;
    @NotNull
    private Integer memberNumber;
    @NotBlank
    private String name;
    @NotBlank
    private String password;
    @NotBlank
    private String email;
}
