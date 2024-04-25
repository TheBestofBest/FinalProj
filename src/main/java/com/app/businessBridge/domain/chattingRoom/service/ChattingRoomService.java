package com.app.businessBridge.domain.chattingRoom.service;

import com.app.businessBridge.domain.chattingRoom.entity.ChattingRoom;
import com.app.businessBridge.domain.chattingRoom.repository.ChattingRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class  ChattingRoomService {
    private final ChattingRoomRepository chattingRoomRepository;

    @Transactional
    public ChattingRoom create() {
        ChattingRoom chattingRoom = ChattingRoom.builder()
                .name("테스트")
                .build();
        return chattingRoomRepository.save(chattingRoom);
    }
}
