package com.app.businessBridge.domain.confirmFormType.request;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateConfirmFormTypeRequest {
    @NotBlank
    private String formName;
    @NotBlank
    private String formDescription;
}
