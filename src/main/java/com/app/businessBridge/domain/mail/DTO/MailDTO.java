package com.app.businessBridge.domain.mail.DTO;

import com.app.businessBridge.domain.mail.Entity.Mail;
import com.app.businessBridge.domain.mailbox.Entity.Mailbox;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class MailDTO {
    private Long id;
    private String title;
    private String content;
    private String attachments;
    private boolean is_Read;
    private LocalDate sendDate;
    private LocalDate receiveDate;

    public MailDTO(Mail mail) {

        this.id = mail.getId();
        this.title = mail.getTitle();
        this.content = mail.getContent();
        this.attachments = mail.getAttachments();
        this.is_Read = mail.is_Read();
        this.sendDate = mail.getSendDate();
        this.receiveDate = mail.getReceiveDate();
    }


}
