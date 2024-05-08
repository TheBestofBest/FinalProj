package com.app.businessBridge.domain.meetingRoom.service;

import com.app.businessBridge.domain.chattingRoom.entity.ChattingRoom;
import com.app.businessBridge.domain.meetingRoom.entity.MeetingRoom;
import com.app.businessBridge.domain.meetingRoom.repository.MeetingRoomRepository;
import com.app.businessBridge.domain.member.Service.MemberService;
import com.app.businessBridge.domain.member.entity.Member;
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
    private final MemberService memberService;


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
            List<Member> members = new ArrayList<>();
            members.add(member);
            MeetingRoom meetingRoom = MeetingRoom.builder()
                    .name(name)
                    .members(members)
                    .build();
            meetingRoomRepository.save(meetingRoom);
            return RsData.of(RsCode.S_02, "방생성 성공", meetingRoom);
        } catch (Exception e) {
            return RsData.of(RsCode.F_01, "방생성 실패");
        }
    }

    @Transactional
    public RsData<MeetingRoom> invite(Long roomId, Member member) {
        RsData<MeetingRoom> rsData = this.getMeetingRoom(roomId);
        if (!rsData.getIsSuccess()) {
            return rsData;
        }
        List<Member> members = rsData.getData().getMembers();
        try {
//            return members.stream().filter(r -> r.equals(member))
//                    .findAny()
//                    .map(m -> RsData.of(
//                            RsCode.F_01, "이미 초대됨", rsData.getData()
//                    )).orElseGet(() -> {
//                        memberService.create(rsData.getData(), member);
//                        return RsData.of(RsCode.S_02, "초대 성공", getChattingRoom(chatRoomId).getData());
//                    });
        } catch (Exception e) {
            return RsData.of(RsCode.F_01, "초대 실패");
        }
    }
}
