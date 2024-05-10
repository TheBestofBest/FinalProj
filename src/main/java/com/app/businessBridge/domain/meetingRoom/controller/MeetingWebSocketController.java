package com.app.businessBridge.domain.meetingRoom.controller;

import com.app.businessBridge.domain.chatLog.dto.ChatLogDto;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MeetingWebSocketController {

    @MessageMapping("/meeting/send/{roomId}")
    @SendTo("/sub/meeting/{roomId}") //해당 채팅방을 구독한 유저에게 send
    public String send(@Payload ChatLogDto message,
                       @DestinationVariable Long roomId) {
        return message.getContent();
    }

}
