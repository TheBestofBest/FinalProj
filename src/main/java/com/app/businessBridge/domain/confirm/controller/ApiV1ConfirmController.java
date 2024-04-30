package com.app.businessBridge.domain.confirm.controller;

import com.app.businessBridge.domain.confirm.dto.ConfirmDTO;
import com.app.businessBridge.domain.confirm.entity.Confirm;
import com.app.businessBridge.domain.confirm.request.ConfirmRequest;
import com.app.businessBridge.domain.confirm.response.ConfirmResponse;
import com.app.businessBridge.domain.confirm.service.ConfirmService;
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
        RsData<ConfirmResponse.create> validateConfirm =  validateConfirm(createConfirmRequest.getFormType(), createConfirmRequest.getConfirmStatus(), createConfirmRequest.getConfirmRequestMember(), createConfirmRequest.getConfirmMembers());
        // 검증 실패 시 실패 코드, 메시지 리턴
        if(!validateConfirm.getIsSuccess()){
            return RsData.of(
                    validateConfirm.getRsCode(),
                    validateConfirm.getMsg(),
                    validateConfirm.getData()
            );
        }


        RsData<Confirm> confirmRsData = this.confirmService.createConfirm(createConfirmRequest);

    }

    // 결재 시 결재 양식, 처리상태, 결재 요청자, 결재 승인자 검증 메서드
    public RsData<ConfirmResponse.create> _validateConfirm(ConfirmFormType formType, ConfirmStatus confirmStatus, Member confirmRequestMember, List<Member> confirmMembers){
        // 결재 양식 검증
        Optional<ConfirmFormType> optionalConfirmFormType = this.confirmFormTypeService.getConfirmFormType(formType.getId());


        //상단의 검증 통과 시 성공코드 리턴
        return RsData.of(
                RsCode.S_08,
                "결재 양식, 처리상태, 요청자, 승인자 검증됨",
                null
        );
    }
}
