package com.app.businessBridge.domain.meetingRoom.repository;

import com.app.businessBridge.domain.meetingRoom.entity.MeetingRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MeetingRoomRepository extends JpaRepository<MeetingRoom, Long> {
}
