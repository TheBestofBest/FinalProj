package com.app.businessBridge.domain.confirmStatus.entity;

import com.app.businessBridge.global.Jpa.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class ConfirmStatus extends BaseEntity {
    // 상태 이름
    @Column(length = 255)
    private String statusName;
    // 상태 간략설명
    @Column(columnDefinition = "TEXT")
    private String formDescription;
}
