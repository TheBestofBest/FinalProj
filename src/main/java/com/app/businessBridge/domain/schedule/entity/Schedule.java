package com.app.businessBridge.domain.schedule.entity;

import com.app.businessBridge.global.Jpa.BaseEntity;
import jakarta.persistence.Entity;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
public class Schedule extends BaseEntity {

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

}
