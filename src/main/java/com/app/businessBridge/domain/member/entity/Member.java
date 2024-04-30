package com.app.businessBridge.domain.member.entity;

import com.app.businessBridge.domain.department.entity.Department;
import com.app.businessBridge.domain.grade.entity.Grade;
import com.app.businessBridge.domain.workingstate.entity.WorkingState;
import com.app.businessBridge.global.Jpa.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@SuperBuilder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class Member extends BaseEntity {

    // 부서
    @ManyToOne
    private Department department;

    // 직급
    @OneToOne
    private Grade grade;

    // 로그인 아이디
    private String username;

    // 사원번호
    private Integer memberNumber;

    // 사원명
    private String name;

    // 비밀번호
    private String password;

    // 이메일
    private String email;

    // 직원 근태
    @OneToOne(mappedBy = "member",cascade = CascadeType.REMOVE)
    @JoinColumn(name = "last_location_id")
    @JsonIgnore
    private WorkingState workingState;

    // 연봉 > 정산 시 필요
    private Long salary;
}
