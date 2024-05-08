package com.app.businessBridge.domain.meetingRoom.controller;

import com.app.businessBridge.domain.chattingRoom.entity.ChattingRoom;
import com.app.businessBridge.domain.chattingRoom.request.ChattingRoomRequest;
import com.app.businessBridge.domain.chattingRoom.response.ChattingRoomResponse;
import com.app.businessBridge.domain.meetingRoom.entity.MeetingRoom;
import com.app.businessBridge.domain.meetingRoom.request.MeetingRoomRequest;
import com.app.businessBridge.domain.meetingRoom.response.MeetingRoomResponse;
import com.app.businessBridge.domain.meetingRoom.service.MeetingRoomService;
import com.app.businessBridge.domain.member.Service.MemberService;
import com.app.businessBridge.domain.member.entity.Member;
import com.app.businessBridge.global.RsData.RsData;
import com.app.businessBridge.global.request.Request;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/meetings")
@RequiredArgsConstructor
public class ApiV1MeetingRoomController {
    private final MeetingRoomService meetingRoomService;
    private final MemberService memberService;
    private final Request rq;



    @PostMapping("")
    public RsData<MeetingRoomResponse.getMeetingRoom> create(@Valid @RequestBody MeetingRoomRequest.Create createRq) {
        Member member = rq.getMember();
        RsData<MeetingRoom> rsData = meetingRoomService.create(createRq.getName(), member);
        if (!rsData.getIsSuccess()) {
            return (RsData) rsData;
        }
        return RsData.of(
                rsData.getRsCode(),
                rsData.getMsg(),
                new MeetingRoomResponse.getMeetingRoom(rsData.getData())
        );
    }

    @PatchMapping("/{id}/invite") //해당 채팅방id에 username으로 초대
    public RsData<MeetingRoomResponse.getMeetingRoom> invite(@PathVariable("id") Long id,@Valid @RequestBody MeetingRoomRequest.Invite inviteRq) {
        Member member = memberService.findByUsername(inviteRq.getUsername()).getData();
        RsData<MeetingRoom> rsData = meetingRoomService.invite(id, member);
        if (!rsData.getIsSuccess()) {
            return RsData.of(
                    rsData.getRsCode(),
                    rsData.getMsg(),
                    new ChattingRoomResponse.getChattingRoom(rsData.getData())
            );
        }
        return RsData.of(
                rsData.getRsCode(),
                rsData.getMsg(),
                new ChattingRoomResponse.getChattingRoom(rsData.getData())
        );
    }
}
