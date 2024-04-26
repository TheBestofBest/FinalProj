package com.app.businessBridge.domain.mailbox.Response;

import com.app.businessBridge.domain.mailbox.DTO.MailboxDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class MailboxsResponse {
    private final List<MailboxDTO> mailboxs;
}
