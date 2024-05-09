package com.app.businessBridge.domain.rebate.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class RebateRequest {

    @NotBlank
    private String yaer;
    @NotBlank
    private String month;

}
