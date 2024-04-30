package com.app.businessBridge.global.handler;

import com.app.businessBridge.domain.chatLog.entity.ChatLog;
import com.app.businessBridge.domain.chattingRoom.dto.ChattingRoomDto;
import com.app.businessBridge.domain.chattingRoom.entity.ChattingRoom;
import com.app.businessBridge.domain.chattingRoom.service.ChattingRoomService;
import com.app.businessBridge.global.RsData.RsCode;
import com.app.businessBridge.global.RsData.RsData;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.*;

@Slf4j
@Component
@RequiredArgsConstructor
public class WebSocketChatHandler extends TextWebSocketHandler {
    private final ChattingRoomService chattingRoomService;
    private final ObjectMapper objectMapper;
    //현재 연결된 세션
    private final Set<WebSocketSession> sessions = new HashSet<>();
    //채팅방 ID
    private final Map<Long, Set<WebSocketSession>> chatSessionMap = new HashMap<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        log.info(RsData.of(RsCode.S_01,"연결완료", session).toString());
        List<ChattingRoom> chattingRooms = chattingRoomService.getListAll();
        List<ChattingRoomDto> dtos = new ArrayList<>();
        for (ChattingRoom c : chattingRooms) {
            dtos.add(new ChattingRoomDto(c));
        }
        dtos.stream()
                .findFirst()
                .ifPresentOrElse(
                        rooms -> sendMessage(session, dtos),
                        () -> sendMessage(session, "채팅방 없음")
                );
        sessions.add(session);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        System.out.println("payload : " + payload);
        log.info("payload {}", payload);

        // 페이로드 -> chatMessageDto로 변환
        ChatLog chatMessageDto = objectMapper.readValue(payload, ChatLog.class);
        System.out.println("chatlog : " + chatMessageDto);
        log.info("session {}", chatMessageDto.toString());
        //임시
        ChattingRoom chattingRoom = chattingRoomService.create("이름").getData();
        System.out.println("chatRoomId1 : " + chattingRoom.getId());
        chatMessageDto.toBuilder().chattingRoom(chattingRoom).build();
        System.out.println("chatRoomId2 : " + chatMessageDto);

        Long chatRoomId = chattingRoom.getId();
//        Long chatRoomId = chatMessageDto.getChattingRoom().getId();

        System.out.println("chatRoomId3 : " + chatRoomId);

        // 메모리 상에 채팅방에 대한 세션 없으면 만들어줌
        if (!chatSessionMap.containsKey(chatRoomId)) {
            chatSessionMap.put(chatRoomId, new HashSet<>());
        }
        Set<WebSocketSession> chatRoomSession = chatSessionMap.get(chatRoomId);

        // message 에 담긴 타입을 확인한다.
        // 이때 message 에서 getType 으로 가져온 내용이
        // ChatDTO 의 열거형인 MessageType 안에 있는 ENTER 과 동일한 값이라면
        if (chatMessageDto.getMessageType().equals(ChatLog.MessageType.ENTER)) {
            // sessions 에 넘어온 session 을 담고,
            chatRoomSession.add(session);
        }
        if (chatRoomSession.size() >= 3) {
            removeClosedSession(chatRoomSession);
        }
        sendMessageToChatRoom(chatMessageDto, chatRoomSession);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        // TODO Auto-generated method stub
        log.info("{} 연결 끊김", session.getId());
        sessions.remove(session);
    }

    // ====== 채팅 관련 메소드 ======
    private void removeClosedSession(Set<WebSocketSession> chatRoomSession) {
        chatRoomSession.removeIf(sess -> !sessions.contains(sess));
    }

    private void sendMessageToChatRoom(ChatLog chatMessageDto, Set<WebSocketSession> chatRoomSession) {
        chatRoomSession.parallelStream().forEach(sess -> sendMessage(sess, chatMessageDto));//2
    }


    public <T> void sendMessage(WebSocketSession session, T message) {
        try {
            session.sendMessage(new TextMessage(objectMapper.writeValueAsString(message)));
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }
}
