package com.app.businessBridge.domain.meetingRoom.response;

import com.app.businessBridge.domain.chattingRoom.dto.ChattingRoomDto;
import com.app.businessBridge.domain.chattingRoom.entity.ChattingRoom;
import com.app.businessBridge.domain.meetingRoom.dto.MeetingRoomDto;
import com.app.businessBridge.domain.meetingRoom.entity.MeetingRoom;
import lombok.Getter;

import java.util.List;


public class MeetingRoomResponse {
    @Getter
    public static class getMeetingRooms {
        private final List<MeetingRoomDto> meetingRoomDtoList;

        public getMeetingRooms(List<MeetingRoom> meetingRoomList) {
            this.meetingRoomDtoList = (meetingRoomList ==null) ? null : meetingRoomList.stream().map(MeetingRoomDto::new).toList();
        }
    }

    @Getter
    public static class getMeetingRoom {
        private final MeetingRoomDto meetingRoomDto;

        public getMeetingRoom(MeetingRoom meetingRoom) {
            this.meetingRoomDto = (meetingRoom == null) ? null : new MeetingRoomDto(meetingRoom);
        }
    }
}
