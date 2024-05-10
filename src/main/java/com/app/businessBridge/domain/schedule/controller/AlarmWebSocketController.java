package com.app.businessBridge.domain.schedule.controller;

import com.app.businessBridge.domain.Article.Entity.Article;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AlarmWebSocketController {
    @Autowired
    private final SimpMessagingTemplate messagingTemplate;

    //  전체 구독, 부서 구독, 개인 구독
    @Getter
    @Setter
    private static class AlarmReq {
        String category;
        Long categoryId;
        String message;
    }
    @MessageMapping("/alarm")
    public void sendMessage(@Payload AlarmReq sq) {

        sendMessageToTopic(sq.getCategory(),sq.getCategoryId(),sq.getMessage());
    }

    @MessageMapping("/alarm/connect")
    public void connect(@Payload AlarmReq sq) {
        sendMessageToTopic(sq.getCategory(),sq.getCategoryId(),sq.getMessage());
    }

    @MessageMapping("/alarm/disconnect")
    public void disconnect(@Payload AlarmReq sq) {
        sendMessageToTopic(sq.getCategory(),sq.getCategoryId(),sq.getMessage());
    }

    public void sendMessageToTopic(String category, Long categoryId, String message) {
        AlarmReq receive = new AlarmReq();
        receive.setCategory(category);
        receive.setCategoryId(categoryId);
        receive.setMessage(message);

        messagingTemplate.convertAndSend("/topic/public/alarm/" + category+"/"+categoryId.toString(), receive );
    }
}
