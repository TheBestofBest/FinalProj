package com.app.businessBridge.domain.confirmStatus.DTO;

import com.app.businessBridge.domain.confirmStatus.entity.ConfirmStatus;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class ConfirmStatusDTO {
    private Long id;
    private String statusName;
    private String formDescription;

    public ConfirmStatusDTO(ConfirmStatus confirmStatus) {
        this.id = confirmStatus.getId();
        this.statusName = confirmStatus.getStatusName();
        this.formDescription = confirmStatus.getFormDescription();

    }
}
