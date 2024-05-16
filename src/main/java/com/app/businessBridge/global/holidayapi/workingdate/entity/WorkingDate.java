package com.app.businessBridge.global.holidayapi.workingdate.entity;

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
public class WorkingDate extends BaseEntity {

    private String year;

    private String month;

    // 해당 년, 월 근무일 수
    private int workDate;

}
