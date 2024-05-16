package com.app.businessBridge.domain.mail.Response;

import com.app.businessBridge.domain.mail.DTO.MailDTO;
import com.app.businessBridge.domain.mail.Entity.Mail;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
public class MailResponse {


    @Getter
    @Setter
    public static class sendMail {
        private MailDTO mailDTO;

        public sendMail(Mail mail) {
            this.mailDTO = new MailDTO(mail);
        }
    }
}