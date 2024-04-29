package com.app.businessBridge.domain.member.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PostMemberRequest {

    @NotNull
    private Long departmentId;
    @NotNull
    private Long gradeId;
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
