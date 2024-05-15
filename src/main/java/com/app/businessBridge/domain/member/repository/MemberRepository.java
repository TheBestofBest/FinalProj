package com.app.businessBridge.domain.member.repository;

import com.app.businessBridge.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByUsername(String username);

    Optional<Member> findByRefreshToken(String refreshToken);

    Optional<Member> findByMemberNumber(Integer memberNumber);

    Optional<Member> findByEmail(String email);

    @Query("SELECT m FROM Member m WHERE m.meetingRoom.id = :roomId AND m.meetingState = true")
    List<Member> findByApprovedMeetingRoomId(Long roomId);

    Optional<Member> findByEmailAndName(String email, String name);
}
