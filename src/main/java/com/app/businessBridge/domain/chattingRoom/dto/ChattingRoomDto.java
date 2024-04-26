package com.app.businessBridge.domain.chattingRoom.dto;

import com.app.businessBridge.domain.chattingRoom.entity.ChattingRoom;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ChattingRoomDto {
    private Long id;
    private String name;
    public ChattingRoomDto(ChattingRoom chattingRoom) {
        this.id = chattingRoom.getId();
        this.name= chattingRoom.getName();
    }
}
