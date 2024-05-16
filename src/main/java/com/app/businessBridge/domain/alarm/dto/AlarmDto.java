package com.app.businessBridge.domain.alarm.dto;

import com.app.businessBridge.domain.alarm.entity.Alarm;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class AlarmDto {

    private Long id;

    // 관계 이름
    private String relationName;

    // 관계 아이디
    private Long relationId;

    // 스케줄 명
    private String content;

    private LocalDateTime createDate;

    private LocalDateTime modifiedDate;

    public AlarmDto (Alarm alarm) {
        this.id = alarm.getId();
        this.relationName = alarm.getRelationName();
        this.relationId = alarm.getRelationId();
        this.content = alarm.getContent();
        this.createDate = alarm.getCreatedDate();
        this.modifiedDate = alarm.getModifiedDate();
    }

}
