package com.app.businessBridge.domain.department.entity;

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
@AllArgsConstructor
@NoArgsConstructor
public class Department extends BaseEntity {
    private Integer departmentCode;
    private String departmentName;
}
