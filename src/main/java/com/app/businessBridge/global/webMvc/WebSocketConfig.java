package com.app.businessBridge.global.webMvc;


import com.app.businessBridge.global.handler.WebSocketChatHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.security.config.annotation.web.socket.AbstractSecurityWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.AbstractWebSocketMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.*;

@Configuration
@EnableWebSocket
@RequiredArgsConstructor
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer,WebSocketConfigurer {

    private final WebSocketChatHandler chatWebSocketHandler;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        // endpoint 설정 : /api/v1/chat/{postId}
        // 이를 통해서 ws://localhost:8090/ws/chats/{roomId} 으로 요청이 들어오면 websocket 통신을 진행한다.
        // setAllowedOrigins("*")는 모든 ip에서 접속 가능하도록 해줌
        registry.addHandler(chatWebSocketHandler, "/ws/chats/{roomId}").setAllowedOrigins("*");
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // 브로커를 설정합니다.
        registry.enableSimpleBroker("/topic"); // "/topic" 프리픽스를 사용하는 메시지 브로커 활성화
        registry.setApplicationDestinationPrefixes("/app"); // "/app" 프리픽스를 사용하는 메시지를 서버로 라우팅
    }


    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // 첫 번째 WebSocket 연결을 등록합니다.
        registry.addEndpoint("/alarm") // socket 연결 url
                .setAllowedOriginPatterns("*"); // SockJS 지원을 활성화하여 모든 클라이언트에서 WebSocket 연결을 지원

//        // 두 번째 WebSocket 연결을 등록합니다.
//        registry.addEndpoint("/websocket2") // "/websocket2" 엔드포인트를 등록
//                .setAllowedOrigins("*") // 모든 원본에서의 접근을 허용 (보안상 필요에 따라 변경)
//                .withSockJS(); // SockJS 지원을 활성화하여 모든 클라이언트에서 WebSocket 연결을 지원
    }
}
