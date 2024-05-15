package com.app.businessBridge.domain.mail.Service;

import com.app.businessBridge.domain.mail.Entity.Mail;
import com.app.businessBridge.domain.mail.Repository.MailRepository;
import com.app.businessBridge.domain.mail.Request.MailRequest;
import com.app.businessBridge.domain.member.entity.Member;
import com.app.businessBridge.domain.member.repository.MemberRepository;
import com.app.businessBridge.global.RsData.RsCode;
import com.app.businessBridge.global.RsData.RsData;
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
    public RsData create(MailRequest.CreateRequest createRequest) {
        Optional<Member> senderOptional = this.memberRepository.findByEmailAndName(createRequest.getSenderEmail(), createRequest.getSenderName());
        Optional<Member> receiverOptional = this.memberRepository.findByEmailAndName(createRequest.getReceiverEmail(), createRequest.getReceiverName());
        Optional<Member> referenceOptional = this.memberRepository.findByEmailAndName(createRequest.getReferenceEmail(), createRequest.getReferenceName());

        // 발신자와 수신자가 존재하는지 확인
        if (senderOptional.isPresent() && receiverOptional.isPresent()) {
            // 발신자와 수신자를 가져와서 메일 엔티티 생성
            Mail.MailBuilder mailBuilder = Mail.builder()
                    .sender(senderOptional.get())
                    .receiver(receiverOptional.get())
                    .title(createRequest.getTitle())
                    .content(createRequest.getContent())
                    .is_Read(createRequest.isRead())
                    .sendDate(createRequest.getSendDate())
                    .receiveDate(createRequest.getReceiveDate());

            // 참조자가 존재하는 경우에만 설정
            referenceOptional.ifPresent(reference -> mailBuilder.reference(reference));

            Mail mail = mailBuilder.build();

            // 메일 저장
            Mail savedMail = mailRepository.save(mail);

            // 발신자와 수신자에 대한 추가 작업 수행
            savedMail.getSender().getSentMails().add(savedMail);
            savedMail.getReceiver().getReceivedMails().add(savedMail);

            // 참조자가 존재하는 경우에만 추가 작업 수행
            referenceOptional.ifPresent(reference -> reference.getReferencedMails().add(savedMail));

            return RsData.of(RsCode.S_01, "메일이 성공적으로 전송되었습니다.", savedMail);
        } else {
            return RsData.of(RsCode.F_01, "발신자 또는 수신자 정보가 올바르지 않습니다.", null);
        }
    }
}
