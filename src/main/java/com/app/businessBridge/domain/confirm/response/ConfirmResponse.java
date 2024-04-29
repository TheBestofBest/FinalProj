package com.app.businessBridge.domain.confirm.response;

import com.app.businessBridge.domain.confirmStatus.dto.ConfirmStatusDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
public class ConfirmResponse {
    @Getter
    @AllArgsConstructor
    public static class getAll{
        private List<ConfirmStatusDTO> confirmStatusDTOS ;


    }

    @Getter
    @AllArgsConstructor
    public static class create{
        private ConfirmStatusDTO confirmStatusDTO;
    }

    @Getter
    @AllArgsConstructor
    public static class patch{
        private ConfirmStatusDTO confirmStatusDTO;
    }

    @Getter
    @AllArgsConstructor
    public static class delete{
        private Long confirmStatusId;
    }
}
