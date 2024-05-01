package com.app.businessBridge.domain.chatLog.dto;

import com.app.businessBridge.domain.chatLog.entity.ChatLog;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ChatLogDto {
    private Long roomId;
    private String content;
    private String author;
    public ChatLogDto(ChatLog chatLog) {
        this.roomId = chatLog.getChattingRoom().getId();
        this.content = chatLog.getContent();
        this.author = chatLog.getMember().getName();
    }
}
