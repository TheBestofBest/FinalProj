package com.app.businessBridge.domain.confirmFormType.response;

import com.app.businessBridge.domain.confirmFormType.dto.ConfirmFormTypeDTO;
import com.app.businessBridge.domain.confirmStatus.dto.ConfirmStatusDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
public class ConfirmFormTypeResponse {

    @Getter
    @AllArgsConstructor
    public static class getAll{
        private List<ConfirmFormTypeDTO> confirmFormTypeDTOS ;


    }

    @Getter
    @AllArgsConstructor
    public static class create{
        private ConfirmFormTypeDTO confirmFormTypeDTO;
    }

    @Getter
    @AllArgsConstructor
    public static class patch{
        private ConfirmFormTypeDTO confirmFormTypeDTO;
    }

    @Getter
    @AllArgsConstructor
    public static class delete{
        private Long confirmFormTypeId;
    }
}
