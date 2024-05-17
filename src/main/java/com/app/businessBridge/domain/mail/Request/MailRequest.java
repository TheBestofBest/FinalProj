package com.app.businessBridge.domain.mail.Request;

import com.app.businessBridge.domain.member.DTO.MemberDTO;
import com.app.businessBridge.domain.member.entity.Member;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
public class MailRequest {

    @Getter
    @Setter
    public static class CreateRequest {
        // 보낸 사람
        private MemberDTO sender;
//        @NotNull
//        private String senderName;
//        @NotNull
//        private String senderEmail;

        // 받는 사람
        private MemberDTO receiver;
//        @NotNull
//        private String receiverName;
//        @NotNull
//        private String receiverEmail;

        // 참조
        private MemberDTO reference;
//        private String referenceName;
//        private String referenceEmail;

        @NotNull
        private String title;
        @NotNull
        private String content;
        private String attachments;
        private LocalDate sendDate;
        private LocalDate receiveDate;

        // 수신 확인
        private boolean isRead;
    }


}
