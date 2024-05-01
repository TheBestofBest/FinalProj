package com.app.businessBridge.domain.confirmFormType.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ConfirmFormTypeRequest {
    @Getter
    @Setter
    public static class create {
        @NotBlank
        private String formName;
        @NotBlank
        private String formDescription;
    }

    @Getter
    @Setter
    public static class patch {
        @NotBlank
        private String formName;
        @NotBlank
        private String formDescription;
    }
}
