package com.app.businessBridge.domain.confirmStatus.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateConfirmStatusRequest {
    @NotBlank
    private String statusName;
    @NotBlank
    private String statusDescription;
}
