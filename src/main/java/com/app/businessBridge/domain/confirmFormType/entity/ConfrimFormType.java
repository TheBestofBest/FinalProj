package com.app.businessBridge.domain.confirmFormType.entity;

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
@ToString(callSuper = true)
public class ConfrimFormType extends BaseEntity {
    // 양식 이름
    @Column(length = 255)
    private String formName;
    // 양식 간략설명
    @Column(columnDefinition = "TEXT")
    private String formDescription;
}
