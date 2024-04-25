package com.app.businessBridge.domain.chattingRoom.entity;

import com.app.businessBridge.domain.chatLog.entity.ChatLog;
import com.app.businessBridge.global.Jpa.BaseEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Entity
@Getter
@SuperBuilder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class ChattingRoom extends BaseEntity {
    private String name;
    @OneToMany(mappedBy = "chattingRoom", cascade = CascadeType.REMOVE)
    private List<ChatLog> chatLogs;
}
