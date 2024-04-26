package com.app.businessBridge.domain.notification.Entity;

import com.app.businessBridge.domain.mailbox.Entity.Mailbox;
import com.app.businessBridge.global.Jpa.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@ToString(callSuper = true)
public class Notification extends BaseEntity {

    // 메일 알람에 대한 임시 기능 (추후 웹소켓을 통해 알림에 대해 구현 할 예정 하면 Notification 은 삭제 예정)

    @ManyToOne
    @JoinColumn(name = "email_id")
    private Mailbox mailbox;

    // 멤버 관계 @ManyToOne

    private boolean is_Read;
}
