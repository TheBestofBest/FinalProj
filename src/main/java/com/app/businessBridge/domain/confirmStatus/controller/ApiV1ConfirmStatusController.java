package com.app.businessBridge.domain.confirmStatus.controller;

import com.app.businessBridge.domain.confirmStatus.DTO.ConfirmStatusDTO;
import com.app.businessBridge.domain.confirmStatus.entity.ConfirmStatus;
import com.app.businessBridge.domain.confirmStatus.service.ConfirmStatusService;
import com.app.businessBridge.global.RsData.RsData;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/confirm-statuses")
public class ApiV1ConfirmStatusController {
    private final ConfirmStatusService confirmStatusService;

    @Getter
    @AllArgsConstructor
    public static class StatusesResponse {
        private final List<ConfirmStatusDTO> confirmStatusDTOS ;
    }

    @GetMapping("")
    public RsData<StatusesResponse> getStatuses(){
        List<ConfirmStatus> confirmStatuses = this.confirmStatusService.getAll();
        List<ConfirmStatusDTO> confirmStatusDTOS = new ArrayList<>();
        for (ConfirmStatus confirmStatus : confirmStatuses) {
            confirmStatusDTOS.add(new ConfirmStatusDTO(confirmStatus));
        }

        return RsData.of("S-1", "성공", new StatusesResponse(confirmStatusDTOS));
    }


    @Data
    public static class CreateConfirmStatusRequest {
        @NotBlank
        private String statusName;
        @NotBlank
        private String formDescription;
    }
    @Getter
    @AllArgsConstructor
    public static class CreateConfirmStatusResponse {
        private final ConfirmStatusDTO confirmStatusDTO;
    }

    @PostMapping("")
    public RsData<CreateConfirmStatusResponse> createStatus(CreateConfirmStatusRequest createConfirmStatusRequest){
        RsData<ConfirmStatus> confirmStatusRsData = this.confirmStatusService.create(createConfirmStatusRequest.statusName,createConfirmStatusRequest.formDescription);
        return RsData.of(
                confirmStatusRsData.getResultCode(),
                confirmStatusRsData.getMsg(),
                new CreateConfirmStatusResponse(new ConfirmStatusDTO(confirmStatusRsData.getData())));
    }



}
