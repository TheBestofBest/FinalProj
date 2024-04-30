package com.app.businessBridge.domain.workingstate.dto;


import com.app.businessBridge.domain.workingstate.entity.WorkingState;
import com.app.businessBridge.global.formatter.Formatter;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

        // YMD = 년, 월, 일
        String StartDate = Formatter.YMDFormatter(workingState.getStartDate());
        String EndDate = Formatter.YMDFormatter(workingState.getEndDate());


        this.name = workingState.getName();
        this.working = workingState.getWorking();
        this.dayoff = workingState.getDayoff();
        this.leftdayoff = workingState.getLeftdayoff();
        this.startDate = StartDate;
        this.endDate = EndDate;
    }
}
