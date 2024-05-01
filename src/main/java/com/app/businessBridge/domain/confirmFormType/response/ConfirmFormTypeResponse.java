package com.app.businessBridge.domain.confirmFormType.response;

import com.app.businessBridge.domain.confirmFormType.dto.ConfirmFormTypeDTO;
import com.app.businessBridge.domain.confirmFormType.entity.ConfirmFormType;
import com.app.businessBridge.domain.confirmStatus.dto.ConfirmStatusDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
public class ConfirmFormTypeResponse {

    @Getter
    public static class getConfirmFormTypes{
        private List<ConfirmFormTypeDTO> confirmFormTypeDTOs;

        public getConfirmFormTypes(List<ConfirmFormType> confirmFormTypeList){
            this.confirmFormTypeDTOs = confirmFormTypeList.stream().map(ConfirmFormTypeDTO::new).toList();
        }
    }

    @Getter
    public static class create{
        private ConfirmFormTypeDTO confirmFormTypeDTO;

        public create(ConfirmFormType confirmFormType){
            this.confirmFormTypeDTO = new ConfirmFormTypeDTO(confirmFormType);
        }
    }

    @Getter
    public static class patch{
        private ConfirmFormTypeDTO confirmFormTypeDTO;

        public patch(ConfirmFormType confirmFormType){
            this.confirmFormTypeDTO = new ConfirmFormTypeDTO(confirmFormType);
        }
    }

    @Getter
    @AllArgsConstructor
    public static class delete{
        private Long confirmFormTypeId;
    }
}
