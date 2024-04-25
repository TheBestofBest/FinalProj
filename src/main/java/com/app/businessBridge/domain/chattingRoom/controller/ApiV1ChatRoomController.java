package com.app.businessBridge.domain.chattingRoom.controller;

import com.app.businessBridge.domain.chattingRoom.dto.ChattingRoomDto;
import com.app.businessBridge.domain.chattingRoom.entity.ChattingRoom;
import com.app.businessBridge.domain.chattingRoom.service.ChattingRoomService;
import com.app.businessBridge.global.RsData.RsData;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/chat")
@RequiredArgsConstructor
public class ApiV1ChatRoomController {
    private final ChattingRoomService chattingRoomService;

    @Data
    public static class CreateRq {
        @NotBlank
        private String name;
    }

    @PostMapping
    public RsData create(@Valid @RequestBody CreateRq createRq) {
        RsData<ChattingRoom> rsData = chattingRoomService.create(createRq.getName());
        if (rsData.isFail()) return rsData;
        return RsData.of(rsData.getResultCode(),
                rsData.getMsg(),
                new ChattingRoomDto(rsData.getData()));
    }
}
