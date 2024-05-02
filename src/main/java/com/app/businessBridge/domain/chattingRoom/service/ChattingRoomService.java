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
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChattingRoomService {
    private final ChattingRoomRepository chattingRoomRepository;
    private final MemberChatService memberChatService;

    //임시 : 해당 유저에 대한 리스트 가져오기 필요
    @Transactional
    public RsData<List<ChattingRoom>> getListByUsername(String username) {
        return chattingRoomRepository.findChattingRoomByUsername(username).isEmpty() ? RsData.of(
                RsCode.F_04,
                "해당 채팅방 없음"
        ) : RsData.of(
                RsCode.S_01,
                "불러오기 성공",
                chattingRoomRepository.findChattingRoomByUsername(username)
        );
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
    public RsData<ChattingRoom> modify(Long id, String name) {
        ChattingRoom chattingRoom = this.getChattingRoom(id).getData();
        try {
            ChattingRoom modified = chattingRoom.toBuilder()
                    .name(name)
                    .build();
            chattingRoomRepository.save(modified);
            return RsData.of(RsCode.S_03, "방이름 수정", modified);
        } catch (Exception e) {
            return RsData.of(RsCode.F_01, "수정 실패");
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

    @Transactional
    public RsData<ChattingRoom> exit(Long chatRoomId, Member member) {
        ChattingRoom chattingRoom = this.getChattingRoom(chatRoomId).getData();
        try {
            List<MemberChatRelation> relations = chattingRoom.getMembers();
            relations = relations.stream()
                    .filter(relation -> !relation.getMember().equals(member))
                    .collect(Collectors.toList());
            if (relations.isEmpty()) {
                chattingRoomRepository.delete(chattingRoom);
                return RsData.of(RsCode.S_04,"채팅방 삭제");
            }
            chattingRoom = chattingRoom.toBuilder()
                    .members(relations)
                    .build();
            chattingRoomRepository.save(chattingRoom);
            return RsData.of(RsCode.S_03, "나가기 성공");
        } catch (Exception e) {
            return RsData.of(RsCode.F_01, "나가기 실패");
        }
    }

}
