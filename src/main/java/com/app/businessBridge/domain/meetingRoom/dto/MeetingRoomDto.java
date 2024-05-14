package com.app.businessBridge.domain.meetingRoom.dto;

import com.app.businessBridge.domain.chattingRoom.entity.ChattingRoom;
import com.app.businessBridge.domain.meetingRoom.entity.MeetingRoom;
import com.app.businessBridge.domain.member.entity.Member;
import com.app.businessBridge.domain.relation.entity.MemberChatRelation;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class MeetingRoomDto {
    private Long id;
    private String name;
    private List<String> members;
    public MeetingRoomDto(MeetingRoom meetingRoom) {
        this.id = meetingRoom.getId();
        this.name= meetingRoom.getName();
        List<String> names = new ArrayList<>();
        for(Member m  : meetingRoom.getMembers()) {
            names.add(m.getName());
        }
        this.members = names;
    }
}
