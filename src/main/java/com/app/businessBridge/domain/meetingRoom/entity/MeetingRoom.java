package com.app.businessBridge.domain.meetingRoom.entity;

import com.app.businessBridge.domain.member.entity.Member;
import com.app.businessBridge.global.Jpa.BaseEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Entity
@Getter
@Setter
@SuperBuilder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class MeetingRoom extends BaseEntity {
    private String name;
    @OneToMany(mappedBy = "meetingRoom", cascade = CascadeType.REMOVE)
    private List<Member> members;
}
