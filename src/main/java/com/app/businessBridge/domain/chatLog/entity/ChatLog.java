package com.app.businessBridge.domain.chatLog.entity;

import com.app.businessBridge.domain.chattingRoom.entity.ChattingRoom;
import com.app.businessBridge.domain.member.entity.Member;
import com.app.businessBridge.global.Jpa.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@SuperBuilder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class ChatLog extends BaseEntity {
    private String content;
//    private int isCheck; //확인한 인원 ex)카톡 숫자 1
    @ManyToOne
    @JsonIgnore
    private ChattingRoom chattingRoom;
    @ManyToOne
    private Member member;
}
