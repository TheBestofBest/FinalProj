package com.app.businessBridge.domain.workingstate.dto;


import com.app.businessBridge.domain.workingstate.entity.WorkingState;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@NoArgsConstructor
public class WorkingStateDto {

    private String name;

    private double working;

    private double dayoff;

    private double leftdayoff;

    private String startDate;

    private String endDate;

    public WorkingStateDto(WorkingState workingState) {
        // 날짜 2024-04-26 형태로 포맷
        // 필요시 시, 분까지 표현하게 변경 필요
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        String formettedStartDate = workingState.getStartDate().format(formatter);
        String formettedEndDate = workingState.getEndDate().format(formatter);


        this.name = workingState.getName();
        this.working = workingState.getWorking();
        this.dayoff = workingState.getDayoff();
        this.leftdayoff = workingState.getLeftdayoff();
        this.startDate = formettedStartDate;
        this.endDate = formettedEndDate;
    }
}
