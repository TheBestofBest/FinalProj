package com.app.businessBridge.domain.member.entity;

import com.app.businessBridge.domain.department.entity.Department;
import com.app.businessBridge.domain.grade.entity.Grade;
import com.app.businessBridge.domain.workingstate.entity.WorkingState;
import com.app.businessBridge.global.Jpa.BaseEntity;
import com.app.businessBridge.global.image.entity.Image;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@Setter
@SuperBuilder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class Member extends BaseEntity {
    @ManyToOne
    private Department department; // 부서
    @OneToOne
    private Grade grade; // 직급
    private String username; // 로그인 아이디
    private String password; // 비밀번호
    private String email; // 이메일

    @OneToOne
    private Image profileImg; // 프로필 사진
    private Integer memberNumber; // 사원번호
    private String name; // 사원명
    private String assignedTask; // 담당 업무
    private String workStatus; // 근무 상태 ( 온라인, 오프라인, 부재중 )
    private String extensionNumber; // 내선 전화 번호
    private String phoneNumber; // 개인 연락처
    private String statusMessage; // 상태메세지

    // 직원 근태
    @OneToOne(mappedBy = "member",cascade = CascadeType.REMOVE)
    @JoinColumn(name = "working_state_id")
    @JsonIgnore
    private WorkingState workingState;
    // 연봉 > 정산 시 필요
    private Long salary;


    // 리프레시 토큰
    private String refreshToken;
}
