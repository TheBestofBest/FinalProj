package com.app.businessBridge.domain.mail.Repository;

import com.app.businessBridge.domain.mail.Entity.Mail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MailRepository extends JpaRepository<Mail,Long> {
}
