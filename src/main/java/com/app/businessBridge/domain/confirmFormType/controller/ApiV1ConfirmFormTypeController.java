package com.app.businessBridge.domain.confirmFormType.controller;

import com.app.businessBridge.domain.confirmFormType.dto.ConfirmFormTypeDTO;
import com.app.businessBridge.domain.confirmFormType.entity.ConfirmFormType;
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
    @Getter
    @AllArgsConstructor
    public static class ConfrimFormTypesResponse {
        private final List<ConfirmFormTypeDTO> confirmFormTypeDTOS;
    }

    @GetMapping("")
    public RsData<ConfrimFormTypesResponse> getStatuses(){
        List<ConfirmFormType> confirmFormTypes = this.confirmFormTypeService.getAll();
        List<ConfirmFormTypeDTO> confirmFormTypeDTOS = new ArrayList<>();
        for (ConfirmFormType confirmFormType : confirmFormTypes) {
            confirmFormTypeDTOS.add(new ConfirmFormTypeDTO(confirmFormType));
        }

        return RsData.of("S-1", "성공", new ConfrimFormTypesResponse(confirmFormTypeDTOS));
    }




//    @PostMapping("")
//    public RsData<> createStatus){
//        RsData<ConfirmFormType> confirmFormTypeRsData = this.confirmStatusService.create();
//        return RsData.of(
//                confirmStatusRsData.getResultCode(),
//                confirmStatusRsData.getMsg(),
//                new ApiV1ConfirmStatusController.CreateConfirmStatusResponse(new ConfirmStatusDTO(confirmStatusRsData.getData())));
//    }
}
