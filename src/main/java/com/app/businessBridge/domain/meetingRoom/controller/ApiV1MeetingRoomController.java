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
import com.app.businessBridge.domain.member.response.MemberResponse;
import com.app.businessBridge.domain.schedule.controller.AlarmWebSocketController;
import com.app.businessBridge.global.RsData.RsData;
import com.app.businessBridge.global.request.Request;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/meetings")
@RequiredArgsConstructor
public class ApiV1MeetingRoomController {
    private final MeetingRoomService meetingRoomService;
    private final MemberService memberService;
    private final Request rq;
    private final AlarmWebSocketController alarmWebSocketController;

    @GetMapping("/{id}")
    public RsData<MeetingRoomResponse.getMeetingRoom> getMeetingRoom(@PathVariable("id") Long id) {
        RsData<MeetingRoom> rsData = meetingRoomService.getMeetingRoom(id);
        if (!rsData.getIsSuccess()) {
            return (RsData) rsData;
        }
        return RsData.of(
                rsData.getRsCode(),
                rsData.getMsg(),
                new MeetingRoomResponse.getMeetingRoom(rsData.getData())
        );
    }

    @GetMapping("/{id}/members")
    public RsData<MemberResponse.GetMembers> getMembers(@PathVariable("id") Long id) {
        RsData<List<Member>> rsData = memberService.getApprovedMembersByMeetingRoom(id);
        if (!rsData.getIsSuccess()) {
            return (RsData) rsData;
        }
        return RsData.of(
                rsData.getRsCode(),
                rsData.getMsg(),
                new MemberResponse.GetMembers(rsData.getData())
        );
    }


    @PostMapping("")
    public RsData<MeetingRoomResponse.getMeetingRoom> create(@Valid @RequestBody MeetingRoomRequest.Create createRq) {
        Member member = rq.getMember();
        RsData<MeetingRoom> rsData = meetingRoomService.create(createRq.getName(), member);
        if (!rsData.getIsSuccess()) {
            return (RsData) rsData;
        }
        Long meetingRoomId = rsData.getData().getId();
        memberService.inviteMeeting(member, rsData.getData().getId());
        memberService.approveMeeting(member, rsData.getData().getId());

        RsData<MeetingRoom> result = meetingRoomService.getMeetingRoom(meetingRoomId);

        return RsData.of(
                result.getRsCode(),
                result.getMsg(),
                new MeetingRoomResponse.getMeetingRoom(result.getData())
        );
    }

    @PatchMapping("/{id}")
    public RsData<MeetingRoomResponse.getMeetingRoom> modify(@PathVariable("id") Long id, @Valid @RequestBody MeetingRoomRequest.Create createRq) {
        RsData<MeetingRoom> rsData = meetingRoomService.modify(id, createRq.getName());
        if (!rsData.getIsSuccess()) {
            return (RsData) rsData;
        }
        return RsData.of(
                rsData.getRsCode(),
                rsData.getMsg(),
                new MeetingRoomResponse.getMeetingRoom(rsData.getData())
        );
    }

    @PatchMapping("/{id}/invite") //해당 회의 id에 username으로 초대
    public RsData<MemberResponse.GetMember> invite(@PathVariable("id") Long id, @Valid @RequestBody MeetingRoomRequest.Invite inviteRq) {
        Member member = memberService.findByUsername(inviteRq.getUsername()).getData();
        alarmWebSocketController.sendMessageToTopic("meeting", member.getId(), "%s 님이 회의에 초대하였습니다.".formatted(rq.getMember().getName()));
        RsData<Member> rsData = memberService.inviteMeeting(member, id);
        if (!rsData.getIsSuccess()) {
            return RsData.of(
                    rsData.getRsCode(),
                    rsData.getMsg(),
                    new MemberResponse.GetMember(rsData.getData())
            );
        }
        return RsData.of(
                rsData.getRsCode(),
                rsData.getMsg(),
                new MemberResponse.GetMember(rsData.getData())
        );
    }

    @PatchMapping("/{id}/approve") //해당 회의 id에 수락
    public RsData<MemberResponse.GetMember> approve(@PathVariable("id") Long id) {
        Member member = rq.getMember();
        RsData<Member> rsData = memberService.approveMeeting(member, id);
        if (!rsData.getIsSuccess()) {
            return RsData.of(
                    rsData.getRsCode(),
                    rsData.getMsg(),
                    new MemberResponse.GetMember(rsData.getData())
            );
        }
        return RsData.of(
                rsData.getRsCode(),
                rsData.getMsg(),
                new MemberResponse.GetMember(rsData.getData())
        );
    }

    @PatchMapping("/{id}/exit")
    public RsData<MemberResponse.GetMembers> exit(@PathVariable("id") Long id) {
        Member member = rq.getMember();
        RsData<List<Member>> rsData = memberService.exitMeeting(member, id);
        if (!rsData.getIsSuccess()) {
            return (RsData) rsData;
        }
        return RsData.of(
                rsData.getRsCode(),
                rsData.getMsg(),
                new MemberResponse.GetMembers(rsData.getData())
        );
    }

    @DeleteMapping("/{id}")
    public RsData<MeetingRoomResponse.getMeetingRoom> delete(@PathVariable("id") Long id) {
        RsData<MeetingRoom> rsData = meetingRoomService.delete(id);
        if (!rsData.getIsSuccess()) {
            return (RsData) rsData;
        }
        return RsData.of(
                rsData.getRsCode(),
                rsData.getMsg()
        );
    }
}
