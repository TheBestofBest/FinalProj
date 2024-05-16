package com.app.businessBridge.domain.rebate.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
public class RebateRequest {

    @NotBlank
    private String year;
    @NotBlank
    private String month;


    @Getter
    @Setter
    public static class PatchRequest {
        @NotBlank
        private String rebateId;

        @NotBlank
        private String bonus;
    }

}
