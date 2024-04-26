package com.app.businessBridge.domain.Club.Entity;

import com.app.businessBridge.global.Jpa.BaseEntity;
import jakarta.persistence.Entity;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@ToString(callSuper = true)
public class Club extends BaseEntity {
    private String name;// 동아리 이름
}
