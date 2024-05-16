package com.app.businessBridge.domain.mailbox.Controller;

import com.app.businessBridge.domain.mail.DTO.MailDTO;
import com.app.businessBridge.domain.mailbox.Response.MailboxResponse;
import com.app.businessBridge.domain.mailbox.Service.MailboxService;
import com.app.businessBridge.domain.member.entity.Member;
import com.app.businessBridge.domain.member.repository.MemberRepository;
import com.app.businessBridge.global.RsData.RsCode;
import com.app.businessBridge.global.RsData.RsData;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;



@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/mailboxes")
public class ApiV1MailboxController {
    private final MailboxService mailboxService;
    private final MemberRepository memberRepository;

    @GetMapping("/{memberId}/mails/all")
    public RsData<MailboxResponse.AllMailsResponse> getAllMails(@PathVariable("memberId") Long memberId) {
        Member member = memberRepository.findById(memberId).orElse(null);
        if (member == null) {
            return RsData.of(RsCode.F_01, "해당 멤버를 찾을 수 없습니다.", null);
        }
        RsData<List<MailDTO>> mailsData = mailboxService.getAllMails(member);
        if (mailsData.getIsSuccess()) {
            List<MailDTO> mailDTOList = mailsData.getData();
            MailboxResponse.AllMailsResponse response = new MailboxResponse.AllMailsResponse(mailDTOList);
            return RsData.of(RsCode.S_01, "전체 메일 조회 성공", response);
        } else {
            return RsData.of(RsCode.F_01, "전체 메일 조회 실패", null);
        }
    }

    @GetMapping("/{memberId}/mails/sent")
    public RsData<MailboxResponse.SentMailsResponse> getSentMails(@PathVariable("memberId") Long memberId) {
        Member member = memberRepository.findById(memberId).orElse(null);
        if (member == null) {
            return RsData.of(RsCode.F_01, "해당 멤버를 찾을 수 없습니다.", null);
        }
        RsData<List<MailDTO>> sentMailsData = mailboxService.getSentMails(member);
        if (sentMailsData.getIsSuccess()) {
            List<MailDTO> sentMailDTOList = sentMailsData.getData();
            return RsData.of(RsCode.S_01, "보낸 메일 조회 성공", new MailboxResponse.SentMailsResponse(sentMailDTOList));
        } else {
            return RsData.of(RsCode.F_01, "보낸 메일 조회 실패", null);
        }
    }

    @GetMapping("/{memberId}/mails/received")
    public RsData<MailboxResponse.ReceivedMailsResponse> getReceivedMails(@PathVariable("memberId") Long memberId) {
        Member member = memberRepository.findById(memberId).orElse(null);
        if (member == null) {
            return RsData.of(RsCode.F_01, "해당 멤버를 찾을 수 없습니다.", null);
        }
        RsData<List<MailDTO>> receivedMailsData = mailboxService.getReceivedMails(member);
        if (receivedMailsData.getIsSuccess()) {
            List<MailDTO> receivedMailDTOList = receivedMailsData.getData();
            return RsData.of(RsCode.S_01, "받은 메일 조회 성공", new MailboxResponse.ReceivedMailsResponse(receivedMailDTOList));
        } else {
            return RsData.of(RsCode.F_01, "받은 메일 조회 실패", null);
        }
    }

    @GetMapping("/{memberId}/mails/referenced")
    public RsData<MailboxResponse.ReferencedMailsResponse> getReferencedMails(@PathVariable("memberId") Long memberId) {
        Member member = memberRepository.findById(memberId).orElse(null);
        if (member == null) {
            return RsData.of(RsCode.F_01, "해당 멤버를 찾을 수 없습니다.", null);
        }
        RsData<List<MailDTO>> referencedMailsData = mailboxService.getReferencedMails(member);
        if (referencedMailsData.getIsSuccess()) {
            List<MailDTO> referencedMailDTOList = referencedMailsData.getData();
            return RsData.of(RsCode.S_01, "참조된 메일 조회 성공", new MailboxResponse.ReferencedMailsResponse(referencedMailDTOList));
        } else {
            return RsData.of(RsCode.F_01, "참조된 메일 조회 실패", null);
        }
    }

//    @GetMapping("/{memberId}/mails/self")
//    public RsData<MailboxResponse.SelfMailsResponse> getSelfMails(@PathVariable("memberId") Long memberId) {
//        Member member = memberRepository.findById(memberId).orElse(null);
//        if (member == null) {
//            return RsData.of(RsCode.F_01, "해당 멤버를 찾을 수 없습니다.", null);
//        }
//        RsData<List<MailDTO>> selfMailsData = mailboxService.getselfMails(member);
//        if (selfMailsData.getIsSuccess()) {
//            List<MailDTO> selfMailDTOList = selfMailsData.getData();
//            return RsData.of(RsCode.S_01, "내게 쓴 메일 조회 성공", new MailboxResponse.SelfMailsResponse(selfMailDTOList));
//        } else {
//            return RsData.of(RsCode.F_01, "내게 쓴 메일 조회 실패", null);
//        }
//    }


}
