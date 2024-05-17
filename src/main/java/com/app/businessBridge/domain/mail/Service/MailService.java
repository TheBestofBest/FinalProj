package com.app.businessBridge.domain.mail.Service;

import com.app.businessBridge.domain.mail.Entity.Mail;
import com.app.businessBridge.domain.mail.Repository.MailRepository;
import com.app.businessBridge.domain.mail.Request.MailRequest;
import com.app.businessBridge.domain.member.entity.Member;
import com.app.businessBridge.domain.member.repository.MemberRepository;
import com.app.businessBridge.global.RsData.RsCode;
import com.app.businessBridge.global.RsData.RsData;
import com.app.businessBridge.global.request.Request;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@AllArgsConstructor
public class MailService {
    private final MailRepository mailRepository;
    private final MemberRepository memberRepository;
    private final Request request;


    public List<Mail> getList() {
        return this.mailRepository.findAll();
    }

    public Optional<Mail> getMail(Long id) {
        return this.mailRepository.findById(id);
    }

    public RsData<Mail> findById(Long id) {
        Optional<Mail> mailOptional = mailRepository.findById(id);

        if (mailOptional.isEmpty()) {
            return RsData.of(RsCode.F_04, "메일이 존재하지 않습니다.");
        }

        Mail mail = mailOptional.get();

        if (!mail.is_Read()) {
            mail.set_Read(true);
            mailRepository.save(mail);
        }

        return RsData.of(RsCode.S_05, "조회 완료.", mail);
    }

    public RsData deleteById(Long id) {
        Optional<Mail> mailOptional = mailRepository.findById(id);

        if (mailOptional.isEmpty()) {
            return RsData.of(RsCode.F_04, "메일이 존재하지 않습니다.");
        }

        mailRepository.delete(mailOptional.get());

        return RsData.of(RsCode.S_01, "해당 메일을 삭제했습니다.");
    }

    @Transactional
    public RsData create(MailRequest.CreateRequest createRequest, Member sender, Member receiver, Member reference) {
        // 메일 생성
        Mail mail = Mail.builder()
                .title(createRequest.getTitle())
                .content(createRequest.getContent())
                .is_Read(createRequest.isRead())
                .sendDate(createRequest.getSendDate())
                .receiveDate(createRequest.getReceiveDate())
                .sender(sender)
                .receiver(receiver)
                .reference(reference)
                .build();

        // 메일 저장
        Mail savedMail = mailRepository.save(mail);

        // 발신자와 수신자에 대한 추가 작업 수행
        sender.getSentMails().add(savedMail); // 발신자의 '보낸 메일' 목록에 추가
        receiver.getReceivedMails().add(savedMail); // 수신자의 '받은 메일' 목록에 추가

        // 참조자가 존재하는 경우에만 추가 작업 수행
        if (reference != null) {
            reference.getReferencedMails().add(savedMail); // 참조자의 '참조한 메일' 목록에 추가
        }

        return RsData.of(
                RsCode.S_02,
                "메일 발송이 완료되었습니다.",
                savedMail
        );
    }
}
