package com.app.businessBridge.domain.confirmStatus.response;

import com.app.businessBridge.domain.confirmStatus.dto.ConfirmStatusDTO;
import lombok.Getter;

import java.util.List;

@Getter
public class ConfirmStatusResponse {

    @Getter
    public static class getAll{
        private final List<ConfirmStatusDTO> confirmStatusDTOS ;
    }
}
