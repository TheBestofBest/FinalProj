package com.app.businessBridge.domain.mailbox.Service;

import com.app.businessBridge.domain.mailbox.Entity.Mailbox;
import com.app.businessBridge.domain.mailbox.Repository.MailboxRepository;
import com.app.businessBridge.global.RsData.RsData;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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

    public Optional<Mailbox> getProject(Long id) {
        return this.mailboxRepository.findById(id);
    }

    @Transactional
    public RsData<Mailbox> sendMail( String title, String content, String attachments, LocalDate sendDate, LocalDate receiveDate) {
        Mailbox mailbox = Mailbox.builder()
                .title(title)
                .content(content)
                .attachments(attachments)
                .sendDate(sendDate)
                .receiveDate(receiveDate)

                .build();

        this.mailboxRepository.save(mailbox);

        return RsData.of(
                "S-01",
                "Success 메일 발송이 되었습니다.",
                mailbox
        );
    }
}
