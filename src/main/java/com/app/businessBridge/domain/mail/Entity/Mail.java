    package com.app.businessBridge.domain.mail.Entity;

    import com.app.businessBridge.domain.mailbox.Entity.Mailbox;
    import com.app.businessBridge.domain.member.entity.Member;
    import com.app.businessBridge.global.Jpa.BaseEntity;
    import jakarta.persistence.Entity;
    import jakarta.persistence.FetchType;
    import jakarta.persistence.JoinColumn;
    import jakarta.persistence.ManyToOne;
    import lombok.*;
    import lombok.experimental.SuperBuilder;

    import java.time.LocalDate;

    @Getter
    @Setter
    @Entity
    @AllArgsConstructor
    @NoArgsConstructor
    @SuperBuilder
    @ToString(callSuper = true)
    public class Mail extends BaseEntity {

        // 메일 제목
        private String title;
        // 메일 내용
        private String content;
        // 첨부 파일 (global 로 뺼 경우 삭제 예정)
        private String attachments;
        // 수신 확인
        private boolean is_Read;
        // 보낸 날짜
        private LocalDate sendDate;
        // 받은 날짜
        private LocalDate receiveDate;
        // 보낸 사람
        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "sender_id")
        private Member sender;
        // 받은 사람
        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "receiver_id")
        private Member receiver;
        // 참조
        @ManyToOne
        @JoinColumn(name = "reference_id")
        private Member reference;

        @ManyToOne
        @JoinColumn(name = "mailbox_id")
        private Mailbox mailbox;

    }
