package com.app.businessBridge.domain.chatLog.service;

import com.app.businessBridge.domain.chatLog.entity.ChatLog;
import com.app.businessBridge.domain.chatLog.repository.ChatLogRepository;
import com.app.businessBridge.domain.chattingRoom.entity.ChattingRoom;
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

    public void save(ChatLog chatLog) {
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
