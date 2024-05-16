package com.app.businessBridge.domain.mailbox.Request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
public class MailboxRequest {
    @Getter
    @Setter
    public static class DeleteRequest {
        @NotNull
        private Long id;
    }
}
