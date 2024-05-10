package com.app.businessBridge.global.webMvc;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketMessageBrokerConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // 브로커를 설정합니다.
        registry.enableSimpleBroker("/topic"); // "/topic" 프리픽스를 사용하는 메시지 브로커 활성화
        registry.setApplicationDestinationPrefixes("/app"); // "/app" 프리픽스를 사용하는 메시지를 서버로 라우팅
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // 첫 번째 WebSocket 연결을 등록합니다.
        registry.addEndpoint("/alarm") // "/websocket1" 엔드포인트를 등록
                .setAllowedOrigins("*") // 모든 원본에서의 접근을 허용 (보안상 필요에 따라 변경)
                .withSockJS(); // SockJS 지원을 활성화하여 모든 클라이언트에서 WebSocket 연결을 지원

        // 두 번째 WebSocket 연결을 등록합니다.
        registry.addEndpoint("/websocket2") // "/websocket2" 엔드포인트를 등록
                .setAllowedOrigins("*") // 모든 원본에서의 접근을 허용 (보안상 필요에 따라 변경)
                .withSockJS(); // SockJS 지원을 활성화하여 모든 클라이언트에서 WebSocket 연결을 지원
    }
}