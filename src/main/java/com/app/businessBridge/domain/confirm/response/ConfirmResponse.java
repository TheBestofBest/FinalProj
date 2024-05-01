package com.app.businessBridge.domain.confirm.response;

import com.app.businessBridge.domain.confirm.dto.ConfirmDTO;
import com.app.businessBridge.domain.confirm.entity.Confirm;
import com.app.businessBridge.domain.confirmStatus.dto.ConfirmStatusDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
public class ConfirmResponse {
    @Getter
    public static class getConfirms{
        private List<ConfirmDTO> confirmDTOs;

        public getConfirms(List<Confirm> confirmList){
            this.confirmDTOs = confirmList.stream().map(ConfirmDTO::new).toList();
        }
    }

    @Getter
    public static class create{
        private ConfirmDTO confirmDTO;

        public create(Confirm confirm){
            this.confirmDTO = new ConfirmDTO(confirm);
        }
    }

    @Getter
    public static class patch{
        private ConfirmDTO confirmDTO;

        public patch(Confirm confirm){
            this.confirmDTO = new ConfirmDTO(confirm);
        }
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
        public changeStatus(Confirm confirm){
            this.confirmDTO = new ConfirmDTO(confirm);
        }
    }

    @Getter
    @AllArgsConstructor
    public static class getConfirm{
        private ConfirmDTO confirmDTO;

        public getConfirm(Confirm confirm){
            this.confirmDTO = new ConfirmDTO(confirm);
        }
    }
}
