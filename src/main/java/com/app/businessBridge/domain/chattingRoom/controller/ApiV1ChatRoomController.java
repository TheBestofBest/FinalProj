package com.app.businessBridge.domain.chattingRoom.controller;

import com.app.businessBridge.domain.chattingRoom.dto.ChattingRoomDto;
import com.app.businessBridge.domain.chattingRoom.entity.ChattingRoom;
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

@RestController
@RequestMapping("api/v1/chats")
@RequiredArgsConstructor
public class ApiV1ChatRoomController {
    private final ChattingRoomService chattingRoomService;
    private final MemberService memberService;

    @GetMapping("") //채팅방 ID
    public void getChattingRooms(@PathVariable("id") Long id) {

    }

    @GetMapping("/{id}") //채팅방 ID
    public void getChattingRoom(@PathVariable("id") Long id) {

    }


    @Data
    public static class CreateRq {
        @NotBlank
        private String name;
    }

    @PostMapping
    public RsData create(@Valid @RequestBody CreateRq createRq) {
        Member member = memberService.findByUsername("user01").getData();
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
