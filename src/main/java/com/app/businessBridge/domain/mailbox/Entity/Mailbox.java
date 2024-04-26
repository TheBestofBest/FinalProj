package com.app.businessBridge.domain.mailbox.Entity;

import com.app.businessBridge.global.Jpa.BaseEntity;
import jakarta.persistence.Entity;
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

    // 멤버 : 메일box 관계 @OneToMany 필요)
    // (부서 , 동아리 등 에 따른 전체 메일 보낼시리스트들 관계도 필요하면 넣을 예정)

}
