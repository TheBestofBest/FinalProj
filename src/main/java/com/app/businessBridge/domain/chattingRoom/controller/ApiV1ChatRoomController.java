package com.app.businessBridge.domain.chattingRoom.controller;

import com.app.businessBridge.domain.chattingRoom.dto.ChattingRoomDto;
import com.app.businessBridge.domain.chattingRoom.entity.ChattingRoom;
import com.app.businessBridge.domain.chattingRoom.request.ChattingRoomRequest;
import com.app.businessBridge.domain.chattingRoom.response.ChattingRoomResponse;
import com.app.businessBridge.domain.chattingRoom.service.ChattingRoomService;
import com.app.businessBridge.domain.member.Service.MemberService;
import com.app.businessBridge.domain.member.entity.Member;
import com.app.businessBridge.global.RsData.RsCode;
import com.app.businessBridge.global.RsData.RsData;
import com.app.businessBridge.global.request.Request;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/chats")
@RequiredArgsConstructor
public class ApiV1ChatRoomController {
    private final ChattingRoomService chattingRoomService;
    private final MemberService memberService;
    private final Request rq;

    @GetMapping("")
    public RsData<ChattingRoomResponse.getChattingRooms> getChattingRooms() {
        Member member = rq.getMember();
        RsData<List<ChattingRoom>> rsData = chattingRoomService.getListByUsername(member.getUsername());
        if (!rsData.getIsSuccess()) {
            return (RsData) rsData;
        }
        return RsData.of(
                rsData.getRsCode(),
                rsData.getMsg(),
                new ChattingRoomResponse.getChattingRooms(rsData.getData())
        );
    }

    @GetMapping("/{id}") //채팅방 ID
    public RsData<ChattingRoomResponse.getChattingRoom> getChattingRoom(@PathVariable("id") Long id) {
        RsData<ChattingRoom> rsData = chattingRoomService.getChattingRoom(id);
        if (!rsData.getIsSuccess()) {
            return (RsData) rsData;
        }
        return RsData.of(
                rsData.getRsCode(),
                rsData.getMsg(),
                new ChattingRoomResponse.getChattingRoom(rsData.getData())
        );
    }

    @PostMapping
    public RsData<ChattingRoomResponse.getChattingRoom> create(@Valid @RequestBody ChattingRoomRequest.Create createRq) {
        Member member = rq.getMember(); //getMember 로 바꾸기
        RsData<ChattingRoom> rsData = chattingRoomService.create(createRq.getName(), member);
        if (!rsData.getIsSuccess()) {
            return (RsData) rsData;
        }
        return RsData.of(
                rsData.getRsCode(),
                rsData.getMsg(),
                new ChattingRoomResponse.getChattingRoom(rsData.getData())
        );
    }

    @PatchMapping("/{id}/invite")
    public RsData<ChattingRoomResponse.getChattingRoom> invite(@PathVariable("id") Long id) {
        Member member = rq.getMember();
        RsData<ChattingRoom> rsData = chattingRoomService.invite(id, member);
        if (!rsData.getIsSuccess()) {
            return (RsData) rsData;
        }
        return RsData.of(
                rsData.getRsCode(),
                rsData.getMsg(),
                new ChattingRoomResponse.getChattingRoom(rsData.getData())
        );
    }

    @PatchMapping("/{id}")
    public RsData<ChattingRoomResponse.getChattingRoom> modify(@PathVariable("id") Long id, @Valid @RequestBody ChattingRoomRequest.Create createRq) {
        RsData<ChattingRoom> rsData = chattingRoomService.modify(id, createRq.getName());
        if (!rsData.getIsSuccess()) {
            return (RsData) rsData;
        }
        return RsData.of(
                rsData.getRsCode(),
                rsData.getMsg(),
                new ChattingRoomResponse.getChattingRoom(rsData.getData())
        );
    }

    @PatchMapping("/{id}/exit")
    public RsData<ChattingRoomResponse.getChattingRoom> exit(@PathVariable("id") Long id) {
        Member member = rq.getMember();
        RsData<ChattingRoom> rsData = chattingRoomService.exit(id, member);
        if (!rsData.getIsSuccess()) {
            return (RsData) rsData;
        }
        return RsData.of(
                rsData.getRsCode(),
                rsData.getMsg(),
                new ChattingRoomResponse.getChattingRoom(rsData.getData())
        );
    }

    @DeleteMapping("/{id}")
    public RsData<ChattingRoomResponse.getChattingRoom> delete(@PathVariable("id") Long id) {
        RsData<ChattingRoom> rsData = chattingRoomService.delete(id);
        if (!rsData.getIsSuccess()) {
            return (RsData) rsData;
        }
        return RsData.of(
                rsData.getRsCode(),
                rsData.getMsg(),
                new ChattingRoomResponse.getChattingRoom(rsData.getData())
        );
    }
}
