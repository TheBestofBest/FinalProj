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
    @MessageMapping("/peer/offer/{key}/{roomId}")
    @SendTo("/topic/peer/offer/{key}/{roomId}")
    public String handleOffer(@Payload String offer, @DestinationVariable(value = "roomId") String roomId,
                              @DestinationVariable(value = "key") String key) {
        log.info("[OFFER] Room ID: {}, key:{}, Offer: {}", roomId, key, offer);
        return offer;
    }

    //iceCandidate 정보를 주고 받기 위한 webSocket
    @MessageMapping("/peer/iceCandidate/{key}/{roomId}")
    @SendTo("/topic/peer/iceCandidate/{key}/{roomId}")
    public String handleIceCandidate(@Payload String candidate, @DestinationVariable(value = "roomId") String roomId,
                                     @DestinationVariable(value = "key") String key) {
        log.info("[ICE CANDIDATE] Room ID: {}, key:{}, Candidate: {}", roomId, key, candidate);
        return candidate;
    }

    //answer 정보를 주고 받기 위한 webSocket
    //key : 각 요청하는 캠의 key , roomId : 룸 아이디
    @MessageMapping("/peer/answer/{key}/{roomId}")
    @SendTo("/topic/peer/answer/{key}/{roomId}")
    public String handleAnswer(@Payload String answer, @DestinationVariable(value = "roomId") String roomId,
                               @DestinationVariable(value = "key") String key) {
        log.info("[ANSWER] Room ID: {}, key:{}, Answer: {}", roomId, key, answer);
        return answer;
    }

    @MessageMapping("/peer/exit")
    @SendTo("/topic/peer/exit")
    public String handleExit(@Payload String message) {
        return message;
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
