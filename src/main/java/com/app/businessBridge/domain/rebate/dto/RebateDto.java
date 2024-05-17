package com.app.businessBridge.domain.rebate.dto;


import com.app.businessBridge.domain.member.entity.Member;
import com.app.businessBridge.domain.rebate.entity.Rebate;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RebateDto {

    private Long rebateId;

    private String year;

    private String month;

    // 직원 이름
    private String memberName;

    // 사번
    private String memberId;

    // 직급
    private String grade;

    // 소속부서
    private String dept;

    // 해당월 근무일 수
    private Integer workDate;

    // 실제 근무한 일 수
    private Integer workedDate;

    // 급여
    private Long salary;

    // 보너스, 지원금 등등
    private Long bonus;

    // 세금
    private Long tax;

    // 보험료
    private Long insurance;

    // 세후 실급여
    private Long totalSalary;

    private boolean isSaved;

    public RebateDto(Rebate rebate) {
        this.rebateId = rebate.getId();
        this.year = rebate.getYear();
        this.month = rebate.getMonth();

        this.memberName = rebate.getMember().getName();
        this.memberId = String.valueOf(rebate.getMember().getMemberNumber());
        this.grade = rebate.getMember().getGrade().getName();
        this.dept = rebate.getMember().getDepartment().getName();
        this.workDate = rebate.getWorkDate();
        this.workedDate = rebate.getWorkedDate();

        this.salary = rebate.getSalary();
        this.bonus = rebate.getBonus();
        this.tax = rebate.getTax();
        this.insurance = rebate.getInsurance();
        this.totalSalary = rebate.getTotalSalary();

        this.isSaved = rebate.isSaved();
    }
}
