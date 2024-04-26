package com.app.businessBridge.domain.mailbox.Repository;

import com.app.businessBridge.domain.mailbox.Entity.Mailbox;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MailboxRepository extends JpaRepository<Mailbox,Long> {
}
