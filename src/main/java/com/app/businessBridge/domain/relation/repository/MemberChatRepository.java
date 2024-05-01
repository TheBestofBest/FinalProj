package com.app.businessBridge.domain.relation.repository;

import com.app.businessBridge.domain.relation.entity.MemberChatRelation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberChatRepository extends JpaRepository<MemberChatRelation, Long> {
}
