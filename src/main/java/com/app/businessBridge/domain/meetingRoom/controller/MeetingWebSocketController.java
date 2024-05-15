package com.app.businessBridge.domain.meetingRoom.controller;

import com.app.businessBridge.domain.chatLog.dto.ChatLogDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
public class MeetingWebSocketController {

    private final SimpMessagingTemplate messagingTemplate;

    //offer 정보를 주고 받기 위한 websocket
    @MessageMapping("/peer/offer/{roomId}")
    @SendTo("/topic/peer/offer/{roomId}")
    public String handleOffer(@Payload String offer, @DestinationVariable("roomId") String roomId) {
        log.info("[OFFER] Room ID: {}, Offer: {}", roomId, offer);
        return offer;
    }

    //iceCandidate 정보를 주고 받기 위한 webSocket
    @MessageMapping("/peer/iceCandidate/{roomId}")
    @SendTo("/topic/peer/iceCandidate/{roomId}")
    public String handleIceCandidate(@Payload String candidate, @DestinationVariable("roomId") String roomId) {
        log.info("[ICE CANDIDATE] Room ID: {}, Candidate: {}", roomId, candidate);
        return candidate;
    }

    //answer 정보를 주고 받기 위한 webSocket
    //camKey : 각 요청하는 캠의 key , roomId : 룸 아이디
    @MessageMapping("/peer/answer/{roomId}")
    @SendTo("/topic/peer/answer/{roomId}")
    public String handleAnswer(@Payload String answer, @DestinationVariable("roomId") String roomId) {
        log.info("[ANSWER] Room ID: {}, Answer: {}", roomId, answer);
        return answer;
    }

    //camKey 를 받기위해 신호를 보내는 webSocket
    @MessageMapping("/call/key")
    @SendTo("/topic/call/key")
    public String callKey(@Payload String message) {
        log.info("[CALL KEY] Message: {}", message);
        return message;
    }

    //자신의 camKey 를 모든 연결된 세션에 보내는 webSocket
    @MessageMapping("/send/key")
    @SendTo("/topic/send/key")
    public String sendKey(@Payload String message) {
        log.info("[SEND KEY] Message: {}", message);
        return message;
    }


}
