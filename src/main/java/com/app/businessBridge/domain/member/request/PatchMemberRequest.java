package com.app.businessBridge.domain.member.request;

import lombok.Data;

@Data
public class PatchMemberRequest {
    private Long departmentId;
    private Long gradeId;
    private String username;
    private Integer memberNumber;
    private String name;
    private String password;
    private String email;
}
