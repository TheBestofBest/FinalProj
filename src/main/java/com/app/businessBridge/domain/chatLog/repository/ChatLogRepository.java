package com.app.businessBridge.domain.chatLog.repository;

import com.app.businessBridge.domain.chatLog.entity.ChatLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatLogRepository extends JpaRepository<ChatLog, Long> {
}
