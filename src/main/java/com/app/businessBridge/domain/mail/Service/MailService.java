package com.app.businessBridge.domain.mail.Service;

import com.app.businessBridge.domain.mail.Entity.Mail;
import com.app.businessBridge.domain.mail.Repository.MailRepository;
import com.app.businessBridge.global.RsData.RsData;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class MailService {
    private final MailRepository mailRepository;

    // 기본 코드들로 우선 미리 넣어두었음 다시 변경 할 예정

    public List<Mail> getList() {
        return this.mailRepository.findAll();
    }

    public Optional<Mail> getMail(Long id) {
        return this.mailRepository.findById(id);
    }

    @Transactional
    public RsData<Mail> sendMail( String title, String content, String attachments, LocalDate sendDate, LocalDate receiveDate) {
        Mail mail = Mail.builder()
                .title(title)
                .content(content)
                .attachments(attachments)
                .sendDate(sendDate)
                .receiveDate(receiveDate)

                .build();

        this.mailRepository.save(mail);

        return RsData.of(
                "S-01",
                "Success 메일 발송이 되었습니다.",
                mail
        );
    }
}
