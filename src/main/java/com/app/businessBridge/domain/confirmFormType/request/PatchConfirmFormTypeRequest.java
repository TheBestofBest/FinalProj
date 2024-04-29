package com.app.businessBridge.domain.confirmFormType.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class PatchConfirmFormTypeRequest {
    @NotBlank
    private String formName;
    @NotBlank
    private String formDescription;
}
