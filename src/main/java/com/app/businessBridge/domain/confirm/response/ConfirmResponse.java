package com.app.businessBridge.domain.confirm.response;

import com.app.businessBridge.domain.confirm.dto.ConfirmDTO;
import com.app.businessBridge.domain.confirmStatus.dto.ConfirmStatusDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
public class ConfirmResponse {
    @Getter
    @AllArgsConstructor
    public static class getAll{
        private List<ConfirmDTO> confirmDTOS ;


    }

    @Getter
    @AllArgsConstructor
    public static class create{
        private ConfirmDTO confirmDTO;
    }

    @Getter
    @AllArgsConstructor
    public static class patch{
        private ConfirmDTO confirmDTO;
    }

    @Getter
    @AllArgsConstructor
    public static class delete{
        private Long confirmId;
    }

    @Getter
    @AllArgsConstructor
    public static class validate{
        private Long invalidId;
    }

    @Getter
    @AllArgsConstructor
    public static class changeStatus{
        private ConfirmDTO confirmDTO;
    }
}
