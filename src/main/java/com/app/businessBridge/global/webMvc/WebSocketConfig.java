package com.app.businessBridge.global.webMvc;


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
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {


    @Bean
    private SimpMessagingTemplate messagingTemplate() {
        return new SimpMessagingTemplate();
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
        registry.addEndpoint("/chat")
                .setAllowedOriginPatterns("*");
    }
}
