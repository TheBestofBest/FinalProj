package com.app.businessBridge.domain.relation.repository;

import com.app.businessBridge.domain.relation.entity.MemberChatRelation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MemberChatRepository extends JpaRepository<MemberChatRelation, Long> {


    @Query("SELECT mcr FROM MemberChatRelation mcr WHERE mcr.chattingRoom.id = :chattingRoomId")
    List<MemberChatRelation> findMemberChatRelationByChattingRoomId(@Param("chattingRoomId") Long id);

    @Query("SELECT mcr FROM MemberChatRelation mcr WHERE mcr.member.username = :username")
    List<MemberChatRelation> findMemberChatRelationByUsername(@Param("username") String username);
}
