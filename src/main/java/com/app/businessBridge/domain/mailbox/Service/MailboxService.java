package com.app.businessBridge.domain.mailbox.Service;

import com.app.businessBridge.domain.mail.DTO.MailDTO;
import com.app.businessBridge.domain.mail.Entity.Mail;

import com.app.businessBridge.domain.mail.Repository.MailRepository;
import com.app.businessBridge.domain.member.entity.Member;
import com.app.businessBridge.domain.member.repository.MemberRepository;
import com.app.businessBridge.global.RsData.RsCode;
import com.app.businessBridge.global.RsData.RsData;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
@AllArgsConstructor
public class MailboxService {
    private final MailRepository mailRepository;
    private final MemberRepository memberRepository;

    public RsData<List<MailDTO>> getAllMails(Member member) {
        try {
            List<Mail> allMails = new ArrayList<>();
            allMails.addAll(member.getSentMails());
            allMails.addAll(member.getReceivedMails());
            allMails.addAll(member.getReferencedMails());

            List<MailDTO> allMailDTOs = allMails.stream()
                    .map(MailDTO::new)
                    .collect(Collectors.toList());

            return RsData.of(RsCode.S_01, "전체 메일 조회 성공", allMailDTOs);
        } catch (Exception e) {
            return RsData.of(RsCode.F_03, "전체 메일 조회 실패", null);
        }
    }

    public RsData<List<MailDTO>> getSentMails(Member member) {
        try {
            List<Mail> sentMails = member.getSentMails();

            List<MailDTO> sentMailDTOs = sentMails.stream()
                    .map(MailDTO::new)
                    .collect(Collectors.toList());

            return RsData.of(RsCode.S_01, "보낸 메일 조회 성공", sentMailDTOs);
        } catch (Exception e) {
            return RsData.of(RsCode.F_03, "보낸 메일 조회 실패", null);
        }
    }

    public RsData<List<MailDTO>> getReceivedMails(Member member) {
        try {
            List<Mail> receivedMails = member.getReceivedMails();

            List<MailDTO> receivedMailDTOs = receivedMails.stream()
                    .map(MailDTO::new)
                    .collect(Collectors.toList());

            return RsData.of(RsCode.S_01, "받은 메일 조회 성공", receivedMailDTOs);
        } catch (Exception e) {
            return RsData.of(RsCode.F_03, "받은 메일 조회 실패", null);
        }
    }

    public RsData<List<MailDTO>> getReferencedMails(Member member) {
        try {
            List<Mail> referencedMails = member.getReferencedMails();

            List<MailDTO> referencedMailDTOs = referencedMails.stream()
                    .map(MailDTO::new)
                    .collect(Collectors.toList());

            return RsData.of(RsCode.S_01, "참조된 메일 조회 성공", referencedMailDTOs);
        } catch (Exception e) {
            return RsData.of(RsCode.F_03, "참조된 메일 조회 실패", null);
        }
    }

//    public RsData<List<MailDTO>> getSelfMails(Member member) {
//        try {
//            List<Mail> selfMails = member.getSelfMails();
//
//            List<MailDTO> selfMailDTOs = selfMails.stream()
//                    .map(MailDTO::new)
//                    .collect(Collectors.toList());
//
//            return RsData.of(RsCode.S_01, "내게 쓴 메일 조회 성공", selfMailDTOs);
//        } catch (Exception e) {
//            return RsData.of(RsCode.F_01, "내게 쓴 메일 조회 실패", null);
//        }
//    }

}
