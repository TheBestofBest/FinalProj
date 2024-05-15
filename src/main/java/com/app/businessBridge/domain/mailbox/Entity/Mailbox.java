package com.app.businessBridge.domain.mailbox.Entity;

import com.app.businessBridge.domain.mail.Entity.Mail;
import com.app.businessBridge.domain.member.entity.Member;
import com.app.businessBridge.global.Jpa.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.List;


@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@ToString(callSuper = true)
public class Mailbox extends BaseEntity {
    // 메일 제목
    private String title;
    // 메일 내용
    private String content;
    // 첨부 파일 (global 로 뺼 경우 삭제 예정)
    private String attachments;
    // 메일함 분류
    private String category;
    // 수신 확인
    private boolean is_Read;
    // 보낸 날짜
    private LocalDate sendDate;
    // 받은 날짜
    private LocalDate receiveDate;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "mailbox")
    private List<Mail> mails;

}
