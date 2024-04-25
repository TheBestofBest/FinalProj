package com.app.businessBridge.domain.member.entity;

import com.app.businessBridge.global.Jpa.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@ToString
public class Member extends BaseEntity {
    private String departmentCode;
    private String positionCode;
    // 화상회의 코드
    // private Integer meetingRoomCode;
    private String memberName;
    private String password;
    private String email;
}
