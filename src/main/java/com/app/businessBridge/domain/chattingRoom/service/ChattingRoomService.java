package com.app.businessBridge.domain.chattingRoom.service;

import com.app.businessBridge.domain.chattingRoom.entity.ChattingRoom;
import com.app.businessBridge.domain.chattingRoom.repository.ChattingRoomRepository;
import com.app.businessBridge.global.RsData.RsData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class  ChattingRoomService {
    private final ChattingRoomRepository chattingRoomRepository;

    @Transactional
    public List<ChattingRoom> getListAll() {
//        this.create("채팅방1");
//        this.create("채팅방2");
//        this.create("채팅방3");
        return chattingRoomRepository.findAll();
    }

    @Transactional
    public RsData<ChattingRoom> create(String name) {
        try {
            ChattingRoom chattingRoom = ChattingRoom.builder()
                    .name(name)
                    .build();
            chattingRoomRepository.save(chattingRoom);

            return RsData.of("S","방생성 성공", chattingRoom);
        }catch (Exception e) {
            return RsData.of("F","방생성 실패");
        }

    }
}
