package com.app.businessBridge.domain.rebate.dto;


import com.app.businessBridge.domain.rebate.entity.Rebate;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RebateDto {

    private String memberName;

    private String memberId;

    private String memberPosition;

    private String memberDept;

    // 급여
    private Long salary;

    // 보너스, 지원금 등등
    private Long bonus;

    // 세금
    private Long tax;

    // 보험료
    private Long insurance;

//    Member member 아래 생성자 매개변수에 추가 필요
    public RebateDto(Rebate rebate) {
//        this.memberName = member.getName();
//        this.memberId = member.getCodeNumber();
//        this.memberPosition = member.getPosition();
//        this.memberDept = member.getDepartment().getName();

        this.salary = rebate.getSalary();
        this.bonus = rebate.getBonus();;
        this.tax = rebate.getTax();
        this.insurance = rebate.getInsurance();
    }
}
