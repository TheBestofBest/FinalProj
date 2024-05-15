package com.app.businessBridge.domain.mail.DTO;

import com.app.businessBridge.domain.mail.Entity.Mail;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class MailDTO {
    private Long id;
    private String title;
    private String content;
    private boolean isRead;
    private LocalDate sendDate;
    private LocalDate receiveDate;
    // 보내는 사람
    private String senderName;
    private String senderEmail;
    // 받는 사람
    private String receiverName;
    private String receiverEmail;
    // 참조 (Carbon Copy)
    private String referenceName;
    private String referenceEmail;

    public MailDTO(Mail mail) {
        this.id = mail.getId();
        this.title = mail.getTitle();
        this.content = mail.getContent();
        this.isRead = mail.is_Read();
        this.sendDate = mail.getSendDate();
        this.receiveDate = mail.getReceiveDate();
        //보내는 사람
        this.senderName = mail.getSender().getName();
        this.senderEmail = mail.getSender().getEmail();
        // 받는 사람
        this.receiverName = mail.getReceiver().getName();
        this.receiverEmail = mail.getReceiver().getEmail();
        // 참조(CC)
        if (mail.getReference() != null) {
            this.referenceName = mail.getReference().getName();
            this.referenceEmail = mail.getReference().getEmail();
        }
    }


}
