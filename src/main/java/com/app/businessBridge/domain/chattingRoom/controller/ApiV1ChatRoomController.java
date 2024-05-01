package com.app.businessBridge.domain.chattingRoom.controller;

import com.app.businessBridge.domain.chattingRoom.dto.ChattingRoomDto;
import com.app.businessBridge.domain.chattingRoom.entity.ChattingRoom;
import com.app.businessBridge.domain.chattingRoom.response.ChattingRoomResponse;
import com.app.businessBridge.domain.chattingRoom.service.ChattingRoomService;
import com.app.businessBridge.domain.member.Service.MemberService;
import com.app.businessBridge.domain.member.entity.Member;
import com.app.businessBridge.global.RsData.RsCode;
import com.app.businessBridge.global.RsData.RsData;
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

    @GetMapping("")
    public RsData<ChattingRoomResponse.getChattingRooms> getChattingRooms() {
        Member member = memberService.findByUsername("user01").getData(); //getMember 로 바꾸기
        RsData<List<ChattingRoom>> rsData = chattingRoomService.getListByUsername(member.getUsername());
        if (!rsData.getIsSuccess()) {
            return (RsData)rsData;
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
            return (RsData)rsData;
        }
        return RsData.of(
                rsData.getRsCode(),
                rsData.getMsg(),
                new ChattingRoomResponse.getChattingRoom(rsData.getData())
        );
    }


    @Data
    public static class CreateRq {
        @NotBlank
        private String name;
    }

    @PostMapping
    public RsData create(@Valid @RequestBody CreateRq createRq) {
        Member member = memberService.findByUsername("user01").getData(); //getMember 로 바꾸기
        RsData<ChattingRoom> rsData = chattingRoomService.create(createRq.getName(), member);
        if (rsData.getRsCode().getCode().startsWith("F")) return rsData;
        return RsData.of(RsCode.S_02,
                rsData.getMsg(),
                new ChattingRoomDto(rsData.getData()));
    }

    @PatchMapping("/{id}")
    public RsData invite(@PathVariable("id") Long id) {
        Member member = memberService.findByUsername("user01").getData();
        RsData<ChattingRoom> rsData = chattingRoomService.invite(id, member);
        if (rsData.getRsCode().getCode().startsWith("F")) return rsData;
        return RsData.of(RsCode.S_02,
                rsData.getMsg(),
                new ChattingRoomDto(rsData.getData()));
    }
}
