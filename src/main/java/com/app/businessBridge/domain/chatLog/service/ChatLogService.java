package com.app.businessBridge.domain.chatLog.service;

import com.app.businessBridge.domain.chatLog.dto.ChatLogDto;
import com.app.businessBridge.domain.chatLog.entity.ChatLog;
import com.app.businessBridge.domain.chatLog.repository.ChatLogRepository;
import com.app.businessBridge.domain.chattingRoom.entity.ChattingRoom;
import com.app.businessBridge.domain.chattingRoom.service.ChattingRoomService;
import com.app.businessBridge.domain.member.Service.MemberService;
import com.app.businessBridge.domain.member.entity.Member;
import com.app.businessBridge.domain.relation.entity.MemberChatRelation;
import com.app.businessBridge.global.RsData.RsCode;
import com.app.businessBridge.global.RsData.RsData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatLogService {
    private final ChatLogRepository chatLogRepository;
    private final ChattingRoomService chattingRoomService;
    private final MemberService memberService;


    public void save(ChatLogDto chatLogDto) {
        ChattingRoom chattingRoom = chattingRoomService.getChattingRoom(chatLogDto.getRoomId()).getData();
        Member member = memberService.findByUsername(chatLogDto.getUsername()).getData();
        ChatLog chatLog = ChatLog.builder()
                .content(chatLogDto.getContent())
                .chattingRoom(chattingRoom)
                .member(member)
                .build();
        chatLogRepository.save(chatLog);
    }

    public void save(Long roomId, String message) {
        ChattingRoom chattingRoom = chattingRoomService.getChattingRoom(roomId).getData();
        ChatLog chatLog = ChatLog.builder()
                .content(message)
                .chattingRoom(chattingRoom)
                .build();
        chatLogRepository.save(chatLog);
    }

    @Transactional
    public RsData<List<ChatLog>> getListByRoomId(Long roomId) {
        List<ChatLog> chatLogs = chatLogRepository.findByChattingRoomId(roomId);
        return chatLogs.isEmpty() ? RsData.of(
                RsCode.F_04,
                "해당 기록 없음"
        ) : RsData.of(
                RsCode.S_01,
                "불러오기 성공",
                chatLogs
        );
    }
}
