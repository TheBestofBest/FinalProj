package com.app.businessBridge.domain.chatLog.repository;

import com.app.businessBridge.domain.chatLog.entity.ChatLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ChatLogRepository extends JpaRepository<ChatLog, Long> {
        List<ChatLog> findByChattingRoomId(Long roomId);
}
