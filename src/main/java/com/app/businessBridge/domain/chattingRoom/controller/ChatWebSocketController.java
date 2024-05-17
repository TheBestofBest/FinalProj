package com.app.businessBridge.domain.chattingRoom.controller;

import com.app.businessBridge.domain.chatLog.dto.ChatLogDto;
import com.app.businessBridge.domain.chatLog.entity.ChatLog;
import com.app.businessBridge.domain.chatLog.service.ChatLogService;
import com.app.businessBridge.domain.chattingRoom.request.ChattingRoomRequest;
import com.app.businessBridge.domain.schedule.controller.AlarmWebSocketController;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ChatWebSocketController {
    private final ChatLogService chatLogService;
    private final SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/chat/send/{roomId}")
    @SendTo("/topic/chat/send/{roomId}") //해당 채팅방을 구독한 유저에게 send
    public String handleSend(@Payload ChatLogDto message,
                             @DestinationVariable("roomId") Long roomId) {
        chatLogService.save(message);
        return message.getContent();
    }

    public void handleIsJoin(String isJoin, Long roomId, String message) {
        ChatLogDto chatLog = new ChatLogDto(roomId, message);
        chatLogService.save(chatLog);
        messagingTemplate.convertAndSend("/topic/chat/" + isJoin + "/" + roomId.toString(), chatLog);
    }
}
