package com.app.businessBridge.domain.chattingRoom.controller;

import com.app.businessBridge.domain.chattingRoom.service.ChattingRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/chat")
@RequiredArgsConstructor
public class ApiV1ChatRoomController {
    private final ChattingRoomService chattingRoomService;


    @PostMapping
    public void create() {

    }
}
