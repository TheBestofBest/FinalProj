package com.app.businessBridge.domain.chattingRoom.repository;

import com.app.businessBridge.domain.chattingRoom.entity.ChattingRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChattingRoomRepository extends JpaRepository<ChattingRoom, Long> {
    // 쿼리필요
//    List<ChattingRoom> findByUsername(String username);
}
