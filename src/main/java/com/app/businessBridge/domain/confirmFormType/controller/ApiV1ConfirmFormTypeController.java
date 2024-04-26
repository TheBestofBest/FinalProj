package com.app.businessBridge.domain.confirmFormType.controller;

import com.app.businessBridge.domain.confirmFormType.service.ConfirmFormTypeService;
import com.app.businessBridge.domain.confirmStatus.dto.ConfirmStatusDTO;
import com.app.businessBridge.domain.confirmStatus.controller.ApiV1ConfirmStatusController;
import com.app.businessBridge.domain.confirmStatus.entity.ConfirmStatus;
import com.app.businessBridge.global.RsData.RsData;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/confirm-form-types")
public class ApiV1ConfirmFormTypeController {
    private final ConfirmFormTypeService confirmFormTypeService;
//    @Getter
//    @AllArgsConstructor
//    public static class ConfrimFormTypesResponse {
//        private final List<ConfirmForm> confirmStatusDTOS ;
//    }
//
//    @GetMapping("")
//    public RsData<ApiV1ConfirmStatusController.StatusesResponse> getStatuses(){
//        List<ConfirmStatus> confirmStatuses = this.confirmStatusService.getAll();
//        List<ConfirmStatusDTO> confirmStatusDTOS = new ArrayList<>();
//        for (ConfirmStatus confirmStatus : confirmStatuses) {
//            confirmStatusDTOS.add(new ConfirmStatusDTO(confirmStatus));
//        }
//
//        return RsData.of("S-1", "성공", new ApiV1ConfirmStatusController.StatusesResponse(confirmStatusDTOS));
//    }
//
//
//    @Data
//    public static class CreateConfirmStatusRequest {
//        @NotBlank
//        private String statusName;
//        @NotBlank
//        private String formDescription;
//    }
//    @Getter
//    @AllArgsConstructor
//    public static class CreateConfirmStatusResponse {
//        private final ConfirmStatusDTO confirmStatusDTO;
//    }
//
//    @PostMapping("")
//    public RsData<ApiV1ConfirmStatusController.CreateConfirmStatusResponse> createStatus(ApiV1ConfirmStatusController.CreateConfirmStatusRequest createConfirmStatusRequest){
//        RsData<ConfirmStatus> confirmStatusRsData = this.confirmStatusService.create(createConfirmStatusRequest.statusName,createConfirmStatusRequest.formDescription);
//        return RsData.of(
//                confirmStatusRsData.getResultCode(),
//                confirmStatusRsData.getMsg(),
//                new ApiV1ConfirmStatusController.CreateConfirmStatusResponse(new ConfirmStatusDTO(confirmStatusRsData.getData())));
//    }
}
