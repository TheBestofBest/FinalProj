package com.app.businessBridge.domain.chattingRoom.controller;

import com.app.businessBridge.domain.chatLog.dto.ChatLogDto;
import com.app.businessBridge.domain.chatLog.entity.ChatLog;
import com.app.businessBridge.domain.chatLog.service.ChatLogService;
import com.app.businessBridge.domain.chattingRoom.request.ChattingRoomRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ChatWebSocketController {
    private final ChatLogService chatLogService;

    @MessageMapping("/chat/send/{roomId}")
    @SendTo("/sub/chat/{roomId}") //해당 채팅방을 구독한 유저에게 send
    public String send(@Payload ChatLogDto message,
                        @DestinationVariable Long roomId) {
        chatLogService.save(message.getRoomId(), message.getUsername(), message);
        return message.getContent();
    }

//    @MessageMapping("/chat/connect/{roomId}")
//    @SendTo("/sub/chat/{roomId}")
//    public String connect(@Payload ChatLogDto message,
//                       @DestinationVariable Long roomId) {
//        return message.getContent();
//    }
//
//    @MessageMapping("/chat/disconnect/{roomId}")
//    @SendTo("/sub/chat/{roomId}")
//    public String disconnect(@Payload ChatLogDto message,
//                       @DestinationVariable(value = "roomId") Long roomId) {
//        return message.getContent();
//    }


}
