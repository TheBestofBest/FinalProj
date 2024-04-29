package com.app.businessBridge.domain.confirmStatus.response;

import com.app.businessBridge.domain.confirmStatus.dto.ConfirmStatusDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CreateConfirmStatusResponse {
    private final ConfirmStatusDTO confirmStatusDTO;
}
