package com.app.businessBridge.domain.mail.Response;

import com.app.businessBridge.domain.mail.Entity.Mail;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SendResponse {
    private final Mail mail;
}
