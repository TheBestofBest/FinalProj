package com.app.businessBridge.global.webMvc;


import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.*;

@Configuration
@EnableWebSocketMessageBroker
public class ChatRoomWebSocketConfig implements WebSocketMessageBrokerConfigurer {

    //    @Override
//    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
//        // endpoint 설정 : /api/v1/chat/{postId}
//        // 이를 통해서 ws://localhost:8090/ws/chats/{roomId} 으로 요청이 들어오면 websocket 통신을 진행한다.
//        // setAllowedOrigins("*")는 모든 ip에서 접속 가능하도록 해줌
//        registry.addHandler(webSocketHandler, "/ws/chats/{roomId}").setAllowedOrigins("*");
//    }
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/chat/{roomId}").setAllowedOrigins("*");
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.setApplicationDestinationPrefixes("/app"); //구독
        registry.enableSimpleBroker("/topic"); //주제
    }
}
