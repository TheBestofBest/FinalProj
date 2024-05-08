package com.app.businessBridge.domain.member.DTO;

import com.app.businessBridge.domain.department.entity.Department;
import com.app.businessBridge.domain.grade.entity.Grade;
import com.app.businessBridge.domain.member.entity.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberDTO {
    private Long id;
    private Department department;
    private Grade grade;
    private String username;
    private Integer memberNumber;
    private String name;
    private String email;

    public MemberDTO(Member member) {
        this.id = member.getId();
        this.department = member.getDepartment();
        this.grade = member.getGrade();
        this.username = member.getUsername();
        this.memberNumber = member.getMemberNumber();
        this.name = member.getName();
        this.email = member.getEmail();
    }
}
