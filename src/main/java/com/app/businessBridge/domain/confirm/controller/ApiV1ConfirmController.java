package com.app.businessBridge.domain.confirm.controller;

import com.app.businessBridge.domain.confirm.dto.ConfirmDTO;
import com.app.businessBridge.domain.confirm.entity.Confirm;
import com.app.businessBridge.domain.confirm.request.ConfirmRequest;
import com.app.businessBridge.domain.confirm.response.ConfirmResponse;
import com.app.businessBridge.domain.confirm.service.ConfirmService;
import com.app.businessBridge.domain.confirm.validation.ConfirmValidate;
import com.app.businessBridge.domain.confirmFormType.dto.ConfirmFormTypeDTO;
import com.app.businessBridge.domain.confirmFormType.entity.ConfirmFormType;
import com.app.businessBridge.domain.confirmFormType.service.ConfirmFormTypeService;
import com.app.businessBridge.domain.confirmStatus.entity.ConfirmStatus;
import com.app.businessBridge.domain.member.entity.Member;
import com.app.businessBridge.global.RsData.RsCode;
import com.app.businessBridge.global.RsData.RsData;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/confirms")
public class ApiV1ConfirmController {
    private final ConfirmService confirmService;
    private final ConfirmFormTypeService confirmFormTypeService;


    // 결재 다건 조회
    @GetMapping("")
    public RsData<ConfirmResponse.getAll> getConfirms(){
        List<Confirm> confirms = this.confirmService.getAll();
        List<ConfirmDTO> confirmDTOS = new ArrayList<>();
        for (Confirm confirm : confirms) {
            confirmDTOS.add(new ConfirmDTO(confirm));
        }

        return RsData.of(
                RsCode.S_01,
                "결재 다건 조회 성공",
                new ConfirmResponse.getAll(confirmDTOS)
        );
    }
    // 결재 등록
    @PostMapping("")
    public RsData<ConfirmResponse.create> createConfirm(@Valid @RequestBody ConfirmRequest.create createConfirmRequest){
        // 결재 양식 타입, 결재 처리 상태, 결재 요청자, 결재 승인자 검증
        RsData<ConfirmResponse.create> validateConfirm =  ConfirmValidate.validateConfirmFormType(createConfirmRequest.getFormType());
        // 검증 실패 시 실패 코드, 메시지 리턴
        if(!validateConfirm.getIsSuccess()){
            return RsData.of(
                    validateConfirm.getRsCode(),
                    validateConfirm.getMsg(),
                    validateConfirm.getData()
            );
        }


        RsData<Confirm> confirmRsData = this.confirmService.createConfirm(createConfirmRequest);

        return RsData.of(
                confirmRsData.getRsCode(),
                confirmRsData.getMsg(),
                new ConfirmResponse.create(new ConfirmDTO(confirmRsData.getData()))
        );
    }
}
