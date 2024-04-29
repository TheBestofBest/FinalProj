package com.app.businessBridge.domain.confirm.controller;

import com.app.businessBridge.domain.confirm.dto.ConfirmDTO;
import com.app.businessBridge.domain.confirm.entity.Confirm;
import com.app.businessBridge.domain.confirm.request.ConfirmRequest;
import com.app.businessBridge.domain.confirm.response.ConfirmResponse;
import com.app.businessBridge.domain.confirm.service.ConfirmService;
import com.app.businessBridge.domain.confirmFormType.dto.ConfirmFormTypeDTO;
import com.app.businessBridge.domain.confirmFormType.entity.ConfirmFormType;
import com.app.businessBridge.global.RsData.RsCode;
import com.app.businessBridge.global.RsData.RsData;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/confirms")
public class ApiV1ConfirmController {
    private final ConfirmService confirmService;


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
        RsData<Confirm> confirmRsData = this.confirmService.createConfirm(createConfirmRequest);

    }
}
