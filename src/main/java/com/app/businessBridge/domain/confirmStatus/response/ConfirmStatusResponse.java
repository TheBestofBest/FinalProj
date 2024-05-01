package com.app.businessBridge.domain.confirmStatus.response;

import com.app.businessBridge.domain.confirmStatus.dto.ConfirmStatusDTO;
import com.app.businessBridge.domain.confirmStatus.entity.ConfirmStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Getter
@Setter
public class ConfirmStatusResponse {

    @Getter
    public static class getConfirmStatuses{
        private List<ConfirmStatusDTO> confirmStatusDTOs ;

        public getConfirmStatuses(List<ConfirmStatus> statusList){
            this.confirmStatusDTOs = statusList.stream().map(ConfirmStatusDTO::new).toList();
        }

    }

    @Getter
    public static class create{
        private ConfirmStatusDTO confirmStatusDTO;

        public create(ConfirmStatus confirmStatus){
            this.confirmStatusDTO = new ConfirmStatusDTO(confirmStatus);
        }
    }

    @Getter
    public static class patch{
        private ConfirmStatusDTO confirmStatusDTO;

        public patch(ConfirmStatus confirmStatus){
            this.confirmStatusDTO = new ConfirmStatusDTO(confirmStatus);
        }
    }

    @Getter
    public static class delete{
        private Long confirmStatusId;

        public delete(Long confirmStatusId){
            this.confirmStatusId = confirmStatusId;
        }
    }
}
