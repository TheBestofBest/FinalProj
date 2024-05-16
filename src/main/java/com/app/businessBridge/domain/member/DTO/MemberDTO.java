package com.app.businessBridge.domain.member.DTO;

import com.app.businessBridge.domain.department.entity.Department;
import com.app.businessBridge.domain.division.entity.Division;
import com.app.businessBridge.domain.grade.entity.Grade;
import com.app.businessBridge.domain.member.entity.Member;
import com.app.businessBridge.global.image.entity.Image;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberDTO {
    private Long id;
    private Division division;
    private Department department;
    private Grade grade;
    private String username;
    private String email;
//    private Image profileImg; // 프로필 사진
    private Integer memberNumber; // 사원번호
    private String name; // 사원명
    private String assignedTask; // 담당 업무
    private String extensionNumber; // 내선 전화 번호
    private String phoneNumber; // 개인 연락처
    private String statusMessage; // 상태메세지

    private char sex; // 성별 '남' or '여'
    private String age; // 나이
    private String meetingState;

    public MemberDTO(Member member) {
        this.id = member.getId();
        this.division = member.getDivision();
        this.department = member.getDepartment();
        this.grade = member.getGrade();
        this.username = member.getUsername();
        this.email = member.getEmail();
        this.memberNumber = member.getMemberNumber();
        this.name = member.getName();
        this.assignedTask = member.getAssignedTask();
        this.extensionNumber = member.getExtensionNumber();
        this.phoneNumber = member.getPhoneNumber();
        this.statusMessage = member.getStatusMessage();
        ch
        this.meetingState =
                member.getMeetingState() == null ? "참석 중인 회의 없음" :
                        !member.getMeetingState() ? "%d번 회의 초대 받음".formatted(member.getMeetingRoom().getId()) :
                                "%d번 회의 참여 중".formatted(member.getMeetingRoom().getId());
    }
}
