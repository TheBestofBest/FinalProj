package com.app.businessBridge.domain.confirmFormType.controller;

import com.app.businessBridge.domain.confirmFormType.dto.ConfirmFormTypeDTO;
import com.app.businessBridge.domain.confirmFormType.entity.ConfirmFormType;
import com.app.businessBridge.domain.confirmFormType.request.CreateConfirmFormTypeRequest;
import com.app.businessBridge.domain.confirmFormType.response.ConfirmFormTypesResponse;
import com.app.businessBridge.domain.confirmFormType.response.CreateConfirmFormTypeResponse;
import com.app.businessBridge.domain.confirmFormType.service.ConfirmFormTypeService;
import com.app.businessBridge.domain.confirmStatus.dto.ConfirmStatusDTO;
import com.app.businessBridge.domain.confirmStatus.controller.ApiV1ConfirmStatusController;
import com.app.businessBridge.domain.confirmStatus.response.CreateConfirmStatusResponse;
import com.app.businessBridge.global.RsData.RsCode;
import com.app.businessBridge.global.RsData.RsData;
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

    @GetMapping("")
    public RsData<ConfirmFormTypesResponse> getConfirmFormTypes(){
        List<ConfirmFormType> confirmFormTypes = this.confirmFormTypeService.getAll();
        List<ConfirmFormTypeDTO> confirmFormTypeDTOS = new ArrayList<>();
        for (ConfirmFormType confirmFormType : confirmFormTypes) {
            confirmFormTypeDTOS.add(new ConfirmFormTypeDTO(confirmFormType));
        }

        return RsData.of(RsCode.S_01, "성공", new ConfirmFormTypesResponse(confirmFormTypeDTOS));
    }




    @PostMapping("")
    public RsData<CreateConfirmFormTypeResponse> createConfirmFormType(CreateConfirmFormTypeRequest createConfirmFormTypeRequest){
        RsData<ConfirmFormType> confirmFormTypeRsData = this.confirmFormTypeService.create(createConfirmFormTypeRequest.getFormName(), createConfirmFormTypeRequest.getFormDescription());
        return RsData.of(
                confirmFormTypeRsData.getRsCode(),
                confirmFormTypeRsData.getMsg(),
                new CreateConfirmFormTypeResponse(new ConfirmFormTypeDTO(confirmFormTypeRsData.getData())));
    }
}
