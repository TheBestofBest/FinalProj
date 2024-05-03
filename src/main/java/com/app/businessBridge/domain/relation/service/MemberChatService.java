package com.app.businessBridge.domain.relation.service;

import com.app.businessBridge.domain.chattingRoom.entity.ChattingRoom;
import com.app.businessBridge.domain.member.entity.Member;
import com.app.businessBridge.domain.relation.entity.MemberChatRelation;
import com.app.businessBridge.domain.relation.repository.MemberChatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service

public class MemberChatService {

    private final MemberChatRepository memberChatRepository;

    public MemberChatRelation create(ChattingRoom chattingRoom, Member member) {
        MemberChatRelation relation = MemberChatRelation.builder()
                .member(member)
                .chattingRoom(chattingRoom)
                .build();
        return memberChatRepository.save(relation);
    }

    public List<MemberChatRelation> getListByChatId(Long chatId) {
        return memberChatRepository.findMemberChatRelationByChattingRoomId(chatId);
    }
    public List<MemberChatRelation> getListByUsername(String username) {
        return memberChatRepository.findMemberChatRelationByUsername(username);
    }

    public void delete(MemberChatRelation memberChatRelation) {
        memberChatRepository.delete(memberChatRelation);
    }
}
