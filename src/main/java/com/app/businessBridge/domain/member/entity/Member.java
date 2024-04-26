package com.app.businessBridge.domain.member.entity;

import com.app.businessBridge.domain.department.entity.Department;
import com.app.businessBridge.domain.position.entity.Position;
import com.app.businessBridge.global.Jpa.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@SuperBuilder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class Member extends BaseEntity {
    @ManyToOne
    private Department department;
    @ManyToOne
    private Position position;
    private String username;
    private Integer memberNumber;
    private String name;
    private String password;
    private String email;
}
