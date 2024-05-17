package com.app.businessBridge.domain.chatLog.dto;

import com.app.businessBridge.domain.chatLog.entity.ChatLog;
import com.app.businessBridge.domain.member.DTO.MemberDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class ChatLogDto {
    private Long roomId;
    private String content;
    private String name;
    private String username;
    private LocalDateTime createdDate;
//    private int isCheck;
    public ChatLogDto(ChatLog chatLog) {
        this.roomId = chatLog.getChattingRoom().getId();
        this.content = chatLog.getContent();
        this.name = chatLog.getMember() == null ? null : chatLog.getMember().getName();
        this.username = chatLog.getMember() == null ? null : chatLog.getMember().getUsername();
        this.createdDate = chatLog.getCreatedDate();
//        this.isCheck = chatLog.getIsCheck();
    }

    public ChatLogDto(Long roomId, String content) {
        this.roomId = roomId;
        this.content = content;
    }
}
