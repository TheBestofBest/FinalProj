package com.app.businessBridge.domain.chattingRoom.service;

import com.app.businessBridge.domain.chattingRoom.entity.ChattingRoom;
import com.app.businessBridge.domain.chattingRoom.repository.ChattingRoomRepository;
import com.app.businessBridge.domain.member.entity.Member;
import com.app.businessBridge.domain.relation.entity.MemberChatRelation;
import com.app.businessBridge.domain.relation.service.MemberChatService;
import com.app.businessBridge.global.RsData.RsCode;
import com.app.businessBridge.global.RsData.RsData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChattingRoomService {
    private final ChattingRoomRepository chattingRoomRepository;
    private final MemberChatService memberChatService;

    //임시 : 해당 유저에 대한 리스트 가져오기 필요
    @Transactional
    public List<ChattingRoom> getListAll() {
//        this.create("채팅방1");
//        this.create("채팅방2");
//        this.create("채팅방3");
        return chattingRoomRepository.findAll();
    }

    @Transactional
    public RsData<ChattingRoom> getChattingRoom(Long id) {
        return chattingRoomRepository.findById(id).map((chattingRoom) -> RsData.of(
                RsCode.S_01,
                "불러오기 성공",
                chattingRoom
        )).orElseGet(() -> RsData.of(
                RsCode.F_04,
                "%d번 채팅방 없음".formatted(id)
        ));
    }

    @Transactional
    public RsData<ChattingRoom> create(String name, Member member) {
        try {
            ChattingRoom chattingRoom = ChattingRoom.builder()
                    .name(name)
                    .build();
            List<MemberChatRelation> relations = new ArrayList<>();
            MemberChatRelation relation = memberChatService.create(chattingRoom, member);
            relations.add(relation);
            chattingRoom.setMembers(relations);
            chattingRoomRepository.save(chattingRoom);
            return RsData.of(RsCode.S_02, "방생성 성공", chattingRoom);
        } catch (Exception e) {
            return RsData.of(RsCode.F_01, "방생성 실패");
        }
    }


    @Transactional
    public RsData<ChattingRoom> invite(Long chatRoomId, Member member) {
        ChattingRoom chattingRoom = this.getChattingRoom(chatRoomId).getData();
        try {
            List<MemberChatRelation> relations = chattingRoom.getMembers();
            relations.add(memberChatService.create(chattingRoom, member));
            chattingRoom = chattingRoom.toBuilder()
                    .members(relations)
                    .build();
            chattingRoomRepository.save(chattingRoom);
            return RsData.of(RsCode.S_02, "초대 성공", chattingRoom);
        } catch (Exception e) {
            return RsData.of(RsCode.F_01, "초대 실패");
        }
    }
}
