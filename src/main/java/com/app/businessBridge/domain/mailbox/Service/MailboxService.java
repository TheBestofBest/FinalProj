package com.app.businessBridge.domain.mailbox.Service;

import com.app.businessBridge.domain.mailbox.Entity.Mailbox;
import com.app.businessBridge.domain.mailbox.Repository.MailboxRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class MailboxService {
    private final MailboxRepository mailboxRepository;

    // 기본 코드들로 우선 미리 넣어두었음 다시 변경 할 예정

    public List<Mailbox> getList() {
        return this.mailboxRepository.findAll();
    }

    public Optional<Mailbox> getMailbox(Long id) {
        return this.mailboxRepository.findById(id);
    }

}
