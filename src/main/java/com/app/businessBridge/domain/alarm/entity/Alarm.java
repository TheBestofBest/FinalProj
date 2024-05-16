package com.app.businessBridge.domain.alarm.entity;

import com.app.businessBridge.global.Jpa.BaseEntity;
import jakarta.persistence.Entity;
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
public class Alarm extends BaseEntity {

    // 관계 이름
    private String relationName;

    // 관계 아이디
    private Long relationId;

    // 스케줄 명
    private String content;

}
