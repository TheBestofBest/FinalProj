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

    private String name;

    private double working;

    private double dayoff;

    private double leftdayoff;

    private LocalDateTime startDate;

    private LocalDateTime endDate;
}
