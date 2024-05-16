package com.app.businessBridge.domain.rebate.dto;


import com.app.businessBridge.domain.rebate.entity.Rebate;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RebatesDto {

    private Long rebateId;

    private String year;

    private String month;

    private Long id;

    // 직원 이름
    private String memberName;

    // 사번
    private String memberId;

    // 직급
    private String grade;

    // 소속부서
    private String dept;

    // 실급여
    private Long totalSalary;

    private boolean isSaved;

    public RebatesDto(Rebate rebate) {
        this.rebateId = rebate.getId();
        this.year = rebate.getYear();
        this.month = rebate.getMonth();

        this.id = rebate.getId();
        this.memberName = rebate.getMember().getName();
        this.memberId = String.valueOf(rebate.getMember().getMemberNumber());
        this.grade = rebate.getMember().getGrade().getName();
        this.dept = rebate.getMember().getDepartment().getName();

        this.totalSalary = rebate.getTotalSalary();
        this.isSaved = rebate.isSaved();
    }



}
