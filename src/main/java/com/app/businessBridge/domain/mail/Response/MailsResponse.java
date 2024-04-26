package com.app.businessBridge.domain.mail.Response;

import com.app.businessBridge.domain.mail.DTO.MailDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class MailsResponse {
    private final List<MailDTO> mails;
}
