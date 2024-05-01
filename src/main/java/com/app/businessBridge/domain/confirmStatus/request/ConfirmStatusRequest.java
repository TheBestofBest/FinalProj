package com.app.businessBridge.domain.confirmStatus.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ConfirmStatusRequest {
    @Getter
    @Setter
    public static class create {
        @NotBlank
        private String statusName;
        @NotBlank
        private String statusDescription;
    }

    @Getter
    @Setter
    public static class patch {
        @NotBlank
        private String statusName;
        @NotBlank
        private String statusDescription;
    }


}
