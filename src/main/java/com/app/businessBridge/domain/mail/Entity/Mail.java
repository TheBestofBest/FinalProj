package com.app.businessBridge.domain.mail.Entity;

import com.app.businessBridge.domain.mailbox.Entity.Mailbox;
import com.app.businessBridge.global.Jpa.BaseEntity;
import jakarta.persistence.Entity;
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

    @ManyToOne
    @JoinColumn(name = "mailbox_id")
    private Mailbox mailbox;

    // 메일: 메일  관계 @ManyToOne 필요)


}
