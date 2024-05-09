package com.app.businessBridge.global.handler;

import com.app.businessBridge.domain.chatLog.dto.ChatLogDto;
import com.app.businessBridge.domain.chatLog.entity.ChatLog;
import com.app.businessBridge.domain.chatLog.service.ChatLogService;
import com.app.businessBridge.domain.chattingRoom.entity.ChattingRoom;
import com.app.businessBridge.domain.chattingRoom.service.ChattingRoomService;
import com.app.businessBridge.domain.member.Service.MemberService;
import com.app.businessBridge.domain.member.entity.Member;
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
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Slf4j
@Component
@RequiredArgsConstructor
public class WebSocketChatHandler extends TextWebSocketHandler {
    private final MemberService memberService;
    private final ChattingRoomService chattingRoomService;
    private final ChatLogService chatLogService;
    private final ObjectMapper objectMapper;
    //현재 연결된 세션
    private final Set<WebSocketSession> sessions = new HashSet<>();
    //채팅방 세션 목록
    private final Map<Long, Set<WebSocketSession>> chatSessionMap = new HashMap<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        System.out.println(session);
        String uri = String.valueOf(session.getUri());
        Long roomId = Long.valueOf((uri.split("chat/", 2)[1]));
        log.info(RsData.of(RsCode.S_01, "연결성공", session).toString());
        TextMessage textMessage = new TextMessage("%d 번 채팅방 입장".formatted(roomId));
//        session.sendMessage(textMessage);
        if (!chatSessionMap.containsKey(roomId)) {
            chatSessionMap.put(roomId, new HashSet<>());
        }
        chatSessionMap.get(roomId).add(session);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        System.out.println("payload : " + payload);
        log.info("payload {}", payload);
        // 페이로드 -> chatMessageDto로 변환
        ChatLogDto chatLogDto = objectMapper.readValue(payload, ChatLogDto.class);
        System.out.println("chatlog : " + chatLogDto);
        log.info("session {}", chatLogDto.toString());
        ChattingRoom chattingRoom = chattingRoomService.getChattingRoom((Long) chatLogDto.getRoomId()).getData();
        Member member = memberService.findByUsername(chatLogDto.getUsername()).getData();

        ChatLog chatLog = ChatLog.builder()
                .content(chatLogDto.getContent())
                .chattingRoom(chattingRoom)
                .member(member)
                .build();
//        chatLogService.save(chatLog);

        Long chatRoomId = chattingRoom.getId();
        // 메모리 상에 채팅방에 대한 세션 없으면 만들어줌
        if (!chatSessionMap.containsKey(chatRoomId)) {
            chatSessionMap.put(chatRoomId, new HashSet<>());
        }
        Set<WebSocketSession> chatRoomSession = chatSessionMap.get(chatRoomId);
        System.out.println("채팅방 아이디 :" + chatSessionMap.get(chatRoomId));
//        if (chatRoomSession.size() >= 3) {
//            removeClosedSession(chatRoomSession);
//        }
        System.out.println("----------roomId: " + chatLogDto.getRoomId());
        sendMessageToChatRoom(chatLogDto, chatRoomSession);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        // TODO Auto-generated method stub
        log.info("{} 연결 끊김", session.getId());
        sessions.remove(session);
        // 모든 채팅방에서 세션 제거
        for (Set<WebSocketSession> sessionSet : chatSessionMap.values()) {
            sessionSet.remove(session);
        }
    }

    // ====== 채팅 관련 메소드 ======
    private void removeClosedSession(Set<WebSocketSession> chatRoomSession) {
        chatRoomSession.removeIf(session -> !sessions.contains(session));
    }

    private void sendMessageToChatRoom(ChatLogDto chatLogDto, Set<WebSocketSession> chatRoomSession) {
        chatRoomSession.parallelStream().forEach(session -> sendMessage(session, chatLogDto));//2
    }


    public <T> void sendMessage(WebSocketSession session, T message) {
        try {
            session.sendMessage(new TextMessage(objectMapper.writeValueAsString(message)));
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }
}
