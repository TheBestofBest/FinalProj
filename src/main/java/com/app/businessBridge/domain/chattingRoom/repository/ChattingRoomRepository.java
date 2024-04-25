package com.app.businessBridge.domain.chattingRoom.repository;

import com.app.businessBridge.domain.chattingRoom.entity.ChattingRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChattingRoomRepository extends JpaRepository<ChattingRoom, Long> {
}
