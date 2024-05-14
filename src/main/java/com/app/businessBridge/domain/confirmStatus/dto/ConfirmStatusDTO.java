package com.app.businessBridge.domain.confirmStatus.dto;

import com.app.businessBridge.domain.confirmStatus.entity.ConfirmStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
public class ConfirmStatusDTO {
    private Long id;
    private String statusName;
    private String statusDescription;

    public ConfirmStatusDTO(ConfirmStatus confirmStatus) {
        this.id = confirmStatus.getId();
        this.statusName = confirmStatus.getStatusName();
        this.statusDescription = confirmStatus.getStatusDescription();

    }
}
