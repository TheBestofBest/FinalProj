package com.app.businessBridge.domain.meetingRoom.service;

import com.app.businessBridge.domain.chattingRoom.entity.ChattingRoom;
import com.app.businessBridge.domain.meetingRoom.entity.MeetingRoom;
import com.app.businessBridge.domain.meetingRoom.repository.MeetingRoomRepository;
import com.app.businessBridge.domain.member.Service.MemberService;
import com.app.businessBridge.domain.member.entity.Member;
import com.app.businessBridge.domain.member.repository.MemberRepository;
import com.app.businessBridge.domain.relation.entity.MemberChatRelation;
import com.app.businessBridge.global.RsData.RsCode;
import com.app.businessBridge.global.RsData.RsData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MeetingRoomService {
    private final MeetingRoomRepository meetingRoomRepository;
    private final MemberRepository memberRepository;


    @Transactional
    public RsData<MeetingRoom> getMeetingRoom(Long id) {
        return meetingRoomRepository.findById(id).map((meetingRoom) -> RsData.of(
                RsCode.S_01,
                "불러오기 성공",
                meetingRoom
        )).orElseGet(() -> RsData.of(
                RsCode.F_04,
                "%d번 채팅방 없음".formatted(id)
        ));
    }

    @Transactional
    public RsData<MeetingRoom> create(String name, Member member) {
        try {
            MeetingRoom meetingRoom = MeetingRoom.builder()
                    .name(name)
                    .build();
            meetingRoomRepository.save(meetingRoom);
            return RsData.of(RsCode.S_02, "방생성 성공", this.getMeetingRoom(meetingRoom.getId()).getData());
        } catch (Exception e) {
            return RsData.of(RsCode.F_01, "방생성 실패");
        }
    }

    @Transactional
    public RsData<MeetingRoom> modify(Long roomId, String name) {
        RsData<MeetingRoom> rsData = this.getMeetingRoom(roomId);
        if (!rsData.getIsSuccess()) {
            return rsData;
        }
        try {
            MeetingRoom modified = rsData.getData().toBuilder()
                    .name(name)
                    .build();
            meetingRoomRepository.save(modified);
            return RsData.of(RsCode.S_03, "방이름 수정", modified);
        } catch (Exception e) {
            return RsData.of(RsCode.F_01, "수정 실패");
        }
    }

    @Transactional
    public RsData<MeetingRoom> updateMembers(Long roomId, List<Member> members) {
        RsData<MeetingRoom> rsData = this.getMeetingRoom(roomId);
        if (!rsData.getIsSuccess()) {
            return rsData;
        }
        try {
            MeetingRoom updated = rsData.getData().toBuilder()
                    .members(members)
                    .build();
            meetingRoomRepository.save(updated);
            return RsData.of(RsCode.S_03, "회원 목록 최신화", updated);
        } catch (Exception e) {
            return RsData.of(RsCode.F_01, "수정 실패");
        }
    }


    @Transactional
    public RsData<MeetingRoom> delete(Long roomId) {
        RsData<MeetingRoom> rsData = this.getMeetingRoom(roomId);
        if (!rsData.getIsSuccess()) {
            return rsData;
        }
        try {
            meetingRoomRepository.delete(rsData.getData());
            return RsData.of(RsCode.S_04, "회의방 삭제");
        } catch (Exception e) {
            return RsData.of(RsCode.F_01, "삭제 실패");
        }
    }


}
