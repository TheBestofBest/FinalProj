package com.app.businessBridge.domain.chattingRoom.response;

import com.app.businessBridge.domain.chattingRoom.dto.ChattingRoomDto;
import com.app.businessBridge.domain.chattingRoom.entity.ChattingRoom;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

public class ChattingRoomResponse {

    @Getter
    public static class getChattingRooms {
        private final List<ChattingRoomDto> chattingRoomDtoList;
        public getChattingRooms(List<ChattingRoom> chattingRoomList) {
            this.chattingRoomDtoList = chattingRoomList.stream().map(ChattingRoomDto::new).toList();
        }
    }

    @Getter
    public static class getChattingRoom {
        private final ChattingRoomDto chattingRoomDto;
        public getChattingRoom(ChattingRoom chattingRoom) {
            this.chattingRoomDto = new ChattingRoomDto(chattingRoom);
        }
    }
}
