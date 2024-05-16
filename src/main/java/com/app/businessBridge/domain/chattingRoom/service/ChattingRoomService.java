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


    @Transactional
    public RsData<List<ChattingRoom>> getListByMemberId(Long memberId) {
        List<MemberChatRelation> chatRooms = memberChatService.getListByMemberId(memberId);
        List<ChattingRoom> chattingRoomList = new ArrayList<>();
        for (MemberChatRelation mcr : chatRooms) {
            chattingRoomList.add(this.getChattingRoom(mcr.getChattingRoom().getId()).getData());
        }
        return chatRooms.isEmpty() ? RsData.of(
                RsCode.F_04,
                "해당 채팅방 없음"
        ) : RsData.of(
                RsCode.S_01,
                "불러오기 성공",
                chattingRoomList
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
    public RsData<ChattingRoom> modify(Long chatRoomId, String name) {
        RsData<ChattingRoom> rsData = this.getChattingRoom(chatRoomId);
        if (!rsData.getIsSuccess()) {
            return rsData;
        }
        try {
            ChattingRoom modified = rsData.getData().toBuilder()
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
        RsData<ChattingRoom> rsData = this.getChattingRoom(chatRoomId);
        if (!rsData.getIsSuccess()) {
            return rsData;
        }
        List<MemberChatRelation> members = memberChatService.getListByChatId(chatRoomId);
        try {
            return members.stream().filter(r -> r.getMember().equals(member))
                    .findAny()
                    .map(m -> RsData.of(
                            RsCode.F_01, "이미 초대됨", rsData.getData()
                    )).orElseGet(() -> {
                        memberChatService.create(rsData.getData(), member);
                        return RsData.of(RsCode.S_02, "초대 성공", getChattingRoom(chatRoomId).getData());
                    });
        } catch (Exception e) {
            return RsData.of(RsCode.F_01, "초대 실패");
        }
    }

    @Transactional
    public RsData<ChattingRoom> exit(Long chatRoomId, Member member) {
        RsData<ChattingRoom> rsData = this.getChattingRoom(chatRoomId);
        if (!rsData.getIsSuccess()) {
            return rsData;
        }
        List<MemberChatRelation> members = memberChatService.getListByChatId(chatRoomId);
        try {
            members.stream().filter(r -> r.getMember().equals(member))
                    .findFirst()
                    .ifPresent(memberChatService::delete);
            return RsData.of(RsCode.S_03, "나가기 성공", getChattingRoom(chatRoomId).getData());
        } catch (Exception e) {
            return RsData.of(RsCode.F_01, "나가기 실패");
        }
    }

    @Transactional
    public RsData<ChattingRoom> delete(Long chatRoomId) {
        RsData<ChattingRoom> rsData = this.getChattingRoom(chatRoomId);
        if (!rsData.getIsSuccess()) {
            return rsData;
        }
        try {
            chattingRoomRepository.delete(rsData.getData());
            return RsData.of(RsCode.S_04, "채팅방 삭제");
        } catch (Exception e) {
            return RsData.of(RsCode.F_01, "삭제 실패");
        }
    }

}
