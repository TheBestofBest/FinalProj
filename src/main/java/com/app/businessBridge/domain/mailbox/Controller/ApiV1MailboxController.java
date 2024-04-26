package com.app.businessBridge.domain.mailbox.Controller;

import com.app.businessBridge.domain.mailbox.DTO.MailboxDTO;
import com.app.businessBridge.domain.mailbox.Entity.Mailbox;
import com.app.businessBridge.domain.mailbox.Request.SendRequest;
import com.app.businessBridge.domain.mailbox.Response.MailboxResponse;
import com.app.businessBridge.domain.mailbox.Response.MailboxsResponse;
import com.app.businessBridge.domain.mailbox.Response.SendResponse;
import com.app.businessBridge.domain.mailbox.Service.MailboxService;
import com.app.businessBridge.global.RsData.RsData;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
// 엔드 포인트 멤버/mailbox 형식으로 변경 예정
@RequestMapping("/api/v1/Mailboxs")
public class ApiV1MailboxController {
    private final MailboxService mailboxService;

    // 기본 코드들로 우선 미리 넣어두었음 다시 변경 할 예정
    @GetMapping("")
    public RsData<MailboxsResponse> getMails() {
        List<MailboxDTO> mailboxDTOList = this.mailboxService
                .getList()
                .stream()
                .map(mailbox -> new MailboxDTO(mailbox))
                .toList();
        return RsData.of("S-01", "성공", new MailboxsResponse(mailboxDTOList));
    }

    @GetMapping("/{id}")
    public RsData<MailboxResponse> getMail(@PathVariable("id") Long id) {
        return mailboxService.getProject(id).map(mailbox -> RsData.of(
                "S-01",
                "Success 조회 성공",
                new MailboxResponse(new MailboxDTO(mailbox))
        )).orElseGet(() -> RsData.of(
                "F-01",
                "Bad Request %d 번 메일은 존재하지 않습니다.".formatted(id),
                null
        ));
    }
    @PostMapping("")
    public RsData<SendResponse> SendMail(@Valid @RequestBody SendRequest sendRequest ) {
        RsData<Mailbox> MailboxRs = this.mailboxService.sendMail(sendRequest.getTitle(), sendRequest.getContent(),sendRequest.getAttachments(),sendRequest.getSendDate(),sendRequest.getReceiveDate());

        if (MailboxRs.isFail()) return (RsData) MailboxRs;

        return RsData.of(
                MailboxRs.getResultCode(),
                MailboxRs.getMsg(),
                new SendResponse(MailboxRs.getData())
        );
    }


}
