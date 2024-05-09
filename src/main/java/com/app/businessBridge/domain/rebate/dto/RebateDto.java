package com.app.businessBridge.domain.rebate.dto;


import com.app.businessBridge.domain.member.entity.Member;
import com.app.businessBridge.domain.rebate.entity.Rebate;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RebateDto {

    // 직원 이름
    private String memberName;

    // 사번
    private String memberId;

    // 직급
    private String grade;

    // 소속부서
    private String dept;

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

    public RebateDto(Rebate rebate) {
        this.memberName = rebate.getMember().getName();
        this.memberId = String.valueOf(rebate.getMember().getMemberNumber());
        this.grade = rebate.getMember().getGrade().getName();
        this.dept = rebate.getMember().getDepartment().getName();

        this.salary = rebate.getSalary();
        this.bonus = rebate.getBonus();;
        this.tax = rebate.getTax();
        this.insurance = rebate.getInsurance();
        this.totalSalary = rebate.getTotalSalary();
    }
}
