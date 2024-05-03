package com.app.businessBridge.domain.chattingRoom.entity;

import com.app.businessBridge.domain.chatLog.entity.ChatLog;
import com.app.businessBridge.domain.relation.entity.MemberChatRelation;
import com.app.businessBridge.global.Jpa.BaseEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.web.socket.WebSocketSession;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@SuperBuilder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class ChattingRoom extends BaseEntity {
    private String name;
    @OneToMany(mappedBy = "chattingRoom", cascade = CascadeType.REMOVE)
    private List<ChatLog> chatLogs;

    @OneToMany(mappedBy = "chattingRoom", cascade = CascadeType.REMOVE)
    private List<MemberChatRelation> members;
}
