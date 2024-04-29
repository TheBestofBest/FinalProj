package com.app.businessBridge.domain.confirmFormType.response;

import com.app.businessBridge.domain.confirmFormType.dto.ConfirmFormTypeDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class CreateConfirmFormTypeResponse {
    private final ConfirmFormTypeDTO confirmFormTypeDTO;
}
