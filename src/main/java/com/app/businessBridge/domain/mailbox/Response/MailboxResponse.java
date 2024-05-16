package com.app.businessBridge.domain.mailbox.Response;

import com.app.businessBridge.domain.mail.DTO.MailDTO;
import com.app.businessBridge.domain.mailbox.DTO.MailboxDTO;
import com.app.businessBridge.domain.mailbox.Entity.Mailbox;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;


@Getter
@AllArgsConstructor
public class MailboxResponse {
    @Getter
    public static class GetMailbox {
        private MailboxDTO mailboxDTO;

        public GetMailbox(Mailbox mailbox) {
            this.mailboxDTO = new MailboxDTO(mailbox);
        }
    }

    @Getter
    public static class ReceivedMailsResponse {
        private List<MailDTO> receivedMails;

        public ReceivedMailsResponse(List<MailDTO> receivedMails) {
            this.receivedMails = receivedMails;
        }
    }

    @Getter
    public static class SentMailsResponse {
        private List<MailDTO> sentMails;

        public SentMailsResponse(List<MailDTO> sentMails) {
            this.sentMails = sentMails;
        }
    }

    @Getter
    public static class ReferencedMailsResponse {
        private List<MailDTO> referencedMails;

        public ReferencedMailsResponse(List<MailDTO> referencedMails) {
            this.referencedMails = referencedMails;
        }
    }

    @Getter
    public static class AllMailsResponse {
        private List<MailDTO> allMails;

        public AllMailsResponse(List<MailDTO> allMails) {
            this.allMails = allMails;
        }
    }

    @Getter
    public static class SelfMailsResponse {
        private List<MailDTO> selfMails;

        public SelfMailsResponse(List<MailDTO> selfMails) {
            this.selfMails = selfMails;
        }
    }


}
