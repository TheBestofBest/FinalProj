package com.app.businessBridge.domain.chatLog.controller;

import com.app.businessBridge.domain.chatLog.entity.ChatLog;
import com.app.businessBridge.domain.chatLog.response.ChatLogResponse;
import com.app.businessBridge.domain.chatLog.service.ChatLogService;
import com.app.businessBridge.global.RsData.RsData;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/logs")
@RequiredArgsConstructor
public class ApiV1ChatLogController {
    private final ChatLogService chatLogService;


    @GetMapping("/{id}")
    public RsData<ChatLogResponse.getChattingLogs> getChattingLogs(@PathVariable("id") Long id) {
        RsData<List<ChatLog>> rsData = chatLogService.getListByRoomId(id);
        if (!rsData.getIsSuccess()) {
            return (RsData) rsData;
        }
        return RsData.of(
                rsData.getRsCode(),
                rsData.getMsg(),
                new ChatLogResponse.getChattingLogs(rsData.getData())
        );
    }
}
