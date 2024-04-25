package com.app.businessBridge.domain.workingstate.entity;
import com.app.businessBridge.global.Jpa.BaseEntity;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class WorkingState extends BaseEntity{

    // 근태명
    // ex) 휴가, 근무, 병가, 육아휴직 등등...
    private String name;

    // 근무일 수
    private double working;

    // 사용한 휴가일 수
    private double dayoff;

    // 남은 휴가일 수
    private double leftdayoff;

    // 시작 날짜
    private LocalDateTime startDate;

    // 종료 날짜
    private LocalDateTime endDate;

}
