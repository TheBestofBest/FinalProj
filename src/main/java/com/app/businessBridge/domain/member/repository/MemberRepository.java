package com.app.businessBridge.domain.member.repository;

import com.app.businessBridge.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
