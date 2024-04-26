package com.app.businessBridge.domain.mailbox.Controller;

import com.app.businessBridge.domain.mailbox.DTO.MailboxDTO;
import com.app.businessBridge.domain.mailbox.Response.MailboxResponse;
import com.app.businessBridge.domain.mailbox.Response.MailboxsResponse;
import com.app.businessBridge.domain.mailbox.Service.MailboxService;
import com.app.businessBridge.global.RsData.RsData;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor

@RequestMapping("/api/v1/Mailbox")
public class ApiV1MailboxController {
    private final MailboxService mailboxService;

    // 기본 코드들로 우선 미리 넣어두었습니다 다시 수정 할 예정

    @GetMapping("")
    public RsData<MailboxsResponse> getMails() {
        List<MailboxDTO> mailboxDTOList = this.mailboxService
                .getList()
                .stream()
                .map(mailbox -> new MailboxDTO(mailbox))
                .toList();
        return RsData.of("S-01", "Success 요청 성공", new MailboxsResponse(mailboxDTOList));
    }

    @GetMapping("/{id}")
    public RsData<MailboxResponse> getMailbox(@PathVariable("id") Long id) {
        return mailboxService.getMailbox(id).map(mailbox -> RsData.of(
                "S-01",
                "Success 조회 성공",
                new MailboxResponse(new MailboxDTO(mailbox))
        )).orElseGet(() -> RsData.of(
                "F-01",
                "Bad Request %d 번 메일함은 존재하지 않습니다.".formatted(id),
                null
        ));
    }
}
