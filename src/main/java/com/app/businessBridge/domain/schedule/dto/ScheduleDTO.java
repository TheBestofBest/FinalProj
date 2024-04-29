package com.app.businessBridge.domain.schedule.dto;

import com.app.businessBridge.domain.schedule.entity.Schedule;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class ScheduleDTO {

    // id
    private Long id;

    // 작성자 이름
    private String authorName;

    // 관계 이름
    private String relationName;

    // 관계 아이디
    private Long relationId;

    // 스케줄 명
    private String name;

    // 시작일
    private LocalDate startDate;

    // 종료일
    private LocalDate endDate;

    public ScheduleDTO(Schedule schedule){
        this.id = schedule.getId();
        this.authorName = schedule.getAuthorName();
        this.relationName = schedule.getRelationName();
        this.relationId = schedule.getRelationId();
        this.name = schedule.getName();
        this.startDate = schedule.getStartDate();
        this.endDate = schedule.getEndDate();
    }

}
