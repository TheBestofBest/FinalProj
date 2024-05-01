package com.app.businessBridge.domain.relation.entity;

import com.app.businessBridge.domain.chattingRoom.entity.ChattingRoom;
import com.app.businessBridge.domain.member.entity.Member;
import com.app.businessBridge.global.Jpa.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
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
//멤버 - 채팅방 관계 엔티티(N:M)
public class MemberChatRelation extends BaseEntity {
    @ManyToOne
    private Member member;
    @ManyToOne
    private ChattingRoom chattingRoom;
}
