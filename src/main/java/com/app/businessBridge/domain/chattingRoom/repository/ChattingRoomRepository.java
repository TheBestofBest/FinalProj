package com.app.businessBridge.domain.chattingRoom.repository;

import com.app.businessBridge.domain.chattingRoom.entity.ChattingRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChattingRoomRepository extends JpaRepository<ChattingRoom, Long> {
    @Query("SELECT cr " +
            "FROM ChattingRoom cr " +
            "JOIN MemberChatRelation mcr ON cr.id = mcr.chattingRoom.id " +
            "JOIN Member m ON m.id = mcr.member.id " +
            "WHERE m.username = :username")
    List<ChattingRoom> findChattingRoomByUsername(@Param("username") String username);
}
