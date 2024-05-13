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
    private String meetingState;

    public MemberDTO(Member member) {
        this.id = member.getId();
        this.department = member.getDepartment();
        this.grade = member.getGrade();
        this.username = member.getUsername();
        this.memberNumber = member.getMemberNumber();
        this.name = member.getName();
        this.email = member.getEmail();
        this.meetingState =
                member.getMeetingState() == null ? "참석 중인 회의 없음" :
                        !member.getMeetingState() ? "%d번 회의 초대 받음".formatted(member.getMeetingRoom().getId()) :
                                "%d번 회의 참여 중".formatted(member.getMeetingRoom().getId());
    }
}
