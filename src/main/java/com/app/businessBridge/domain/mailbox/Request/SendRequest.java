package com.app.businessBridge.domain.mailbox.Request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;


import java.time.LocalDate;

@Data
public class SendRequest {
    @NotBlank
    private String title;
    @NotBlank
    private String content;
    private String attachments;
    private LocalDate sendDate;
    private LocalDate receiveDate;
}
