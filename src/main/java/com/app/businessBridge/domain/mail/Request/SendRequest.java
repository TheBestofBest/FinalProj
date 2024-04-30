package com.app.businessBridge.domain.mail.Request;

import com.app.businessBridge.domain.member.entity.Member;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDate;

@Data
public class SendRequest {
    private Member sender;
    @NotBlank
    private Member recipient;
    private Member CC;
    @NotBlank
    private String title;
    @NotBlank
    private String content;
    private String attachments;
    private LocalDate sendDate;
    private LocalDate receiveDate;
}
