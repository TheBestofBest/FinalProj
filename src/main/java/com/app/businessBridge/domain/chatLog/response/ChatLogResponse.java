package com.app.businessBridge.domain.chatLog.response;

import com.app.businessBridge.domain.chatLog.dto.ChatLogDto;
import com.app.businessBridge.domain.chatLog.entity.ChatLog;
import com.app.businessBridge.domain.chattingRoom.dto.ChattingRoomDto;
import com.app.businessBridge.domain.chattingRoom.entity.ChattingRoom;
import lombok.Getter;

import java.util.List;

public class ChatLogResponse {

    @Getter
    public static class getChattingLogs {
        private final List<ChatLogDto> chatLogDtoList;

        public getChattingLogs(List<ChatLog> chattingLogList) {
            this.chatLogDtoList = (chattingLogList ==null) ? null : chattingLogList.stream().map(ChatLogDto::new).toList();
        }
    }
}
