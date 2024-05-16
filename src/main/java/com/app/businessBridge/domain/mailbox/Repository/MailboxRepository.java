package com.app.businessBridge.domain.mailbox.Repository;

import com.app.businessBridge.domain.mailbox.Entity.Mailbox;
import com.app.businessBridge.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MailboxRepository extends JpaRepository<Mailbox, Long> {
    List<Mailbox> findByMember(Member member);
}
