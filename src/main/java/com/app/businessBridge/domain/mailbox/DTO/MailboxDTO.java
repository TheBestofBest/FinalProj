package com.app.businessBridge.domain.mailbox.DTO;

import com.app.businessBridge.domain.mailbox.Entity.Mailbox;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class MailboxDTO {
    private Long id;
    private String title;
    private String content;
    private String attachments;
    private String category;
    private boolean is_Read;
    private LocalDate sendDate;
    private LocalDate receiveDate;

    public MailboxDTO(Mailbox mailbox) {

        this.id = mailbox.getId();
        this.title = mailbox.getTitle();
        this.content = mailbox.getContent();
        this.attachments = mailbox.getAttachments();
        this.category = mailbox.getCategory();
        this.is_Read = mailbox.is_Read();
        this.sendDate = mailbox.getSendDate();
        this.receiveDate = mailbox.getReceiveDate();
    }


}
