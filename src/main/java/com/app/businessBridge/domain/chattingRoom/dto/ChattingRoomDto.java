package com.app.businessBridge.domain.chattingRoom.dto;

import com.app.businessBridge.domain.chattingRoom.entity.ChattingRoom;
import com.app.businessBridge.domain.member.entity.Member;
import com.app.businessBridge.domain.relation.entity.MemberChatRelation;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class ChattingRoomDto {
    private Long id;
    private String name;
    private List<String> members;
    public ChattingRoomDto(ChattingRoom chattingRoom) {
        this.id = chattingRoom.getId();
        this.name= chattingRoom.getName();
        List<String> names = new ArrayList<>();
        for(MemberChatRelation m  : chattingRoom.getMembers()) {
            names.add(m.getMember().getName());
        }
        this.members = names;
    }
}
