package com.app.businessBridge.domain.confirmFormType.response;

import com.app.businessBridge.domain.confirmFormType.dto.ConfirmFormTypeDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PatchConfirmFormTypeResponse {
    private final ConfirmFormTypeDTO confirmFormTypeDTO;
}
