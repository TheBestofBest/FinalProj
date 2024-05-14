package com.app.businessBridge.global.image.entity;


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
public class Image extends BaseEntity {

    // 메일, 동아리, 쪽지 등등 타입
    private String type;

    // 해당 타입의 Id ex) mailEntity.id,
    private Long typeId;

    private String filePath;



}
