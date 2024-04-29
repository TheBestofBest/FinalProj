package com.app.businessBridge.domain.member.request;

import com.app.businessBridge.domain.department.entity.Department;
import com.app.businessBridge.domain.grade.entity.Grade;
import lombok.Data;

@Data
public class PatchMemberRequest {
    private Department department;
    private Grade grade;
    private String username;
    private Integer memberNumber;
    private String name;
    private String password;
    private String email;
}
