package com.app.businessBridge.domain.chatLog.dto;

import com.app.businessBridge.domain.chatLog.entity.ChatLog;
import com.app.businessBridge.domain.member.DTO.MemberDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ChatLogDto {
    private Long roomId;
    private String content;
    private String name;
    private String username;
//    private int isCheck;
    public ChatLogDto(ChatLog chatLog) {
        this.roomId = chatLog.getChattingRoom().getId();
        this.content = chatLog.getContent();
        this.name = chatLog.getMember().getName();
        this.username = chatLog.getMember().getUsername();
//        this.isCheck = chatLog.getIsCheck();
    }
}
