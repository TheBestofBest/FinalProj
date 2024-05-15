package com.app.businessBridge.domain.rebate.entity;

import com.app.businessBridge.domain.member.entity.Member;
import com.app.businessBridge.global.Jpa.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@Setter
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class Rebate extends BaseEntity {

    @ManyToOne
    private Member member;

    // 연도
    private String year;

    // 월
    private String month;

    // 근무 해야하는 일 수
    private Integer workDate;

    // 실제 근무일
    private Integer workedDate;

    // 급여
    private Long salary;

    // 보너스, 지원금 등등
    private Long bonus;

    // 세금
    private Long tax;

    // 보험료
    private Long insurance;

    // 세후 최종 실급여
    private Long totalSalary;

    private boolean isSaved;

}

