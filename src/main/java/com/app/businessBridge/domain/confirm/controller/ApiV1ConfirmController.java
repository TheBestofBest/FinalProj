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
import com.app.businessBridge.domain.member.Service.MemberService;
import com.app.businessBridge.domain.member.entity.Member;
import com.app.businessBridge.global.RsData.RsCode;
import com.app.businessBridge.global.RsData.RsData;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.parameters.P;
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
    public RsData<ConfirmResponse.getAll> getConfirms() {
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
    public RsData<ConfirmResponse.create> createConfirm(@Valid @RequestBody ConfirmRequest.create createConfirmRequest) {
        // 결재 양식 타입, 결재 처리 상태, 결재 요청자, 결재 승인자 검증
        RsData<ConfirmResponse.create> createRsData = ConfirmValidate.validateConfirmCreate(createConfirmRequest);
        if (!createRsData.getIsSuccess()) {
            // 검증 실패 시 해당 검증 실패 코드, 메시지 반환
            return RsData.of(
                    createRsData.getRsCode(),
                    createRsData.getMsg(),
                    createRsData.getData()
            );
        }


        RsData<Confirm> confirmRsData = this.confirmService.createConfirm(createConfirmRequest);

        return RsData.of(
                confirmRsData.getRsCode(),
                confirmRsData.getMsg(),
                new ConfirmResponse.create(new ConfirmDTO(confirmRsData.getData()))
        );
    }

    @PatchMapping("/{confirmId}")
    public RsData<ConfirmResponse.patch> patchConfirm(@PathVariable(value="confirmId") Long confirmId ,@Valid @RequestBody ConfirmRequest.patch patchConfirmRequest) {
        Optional<Confirm> optionalConfirm = this.confirmService.findById(confirmId);
        if(optionalConfirm.isEmpty()){
            return RsData.of(
                    RsCode.F_04,
                    "id: %d번 결재 는 존재하지 않습니다.".formatted(confirmId),
                    null
            );
        }

        RsData<ConfirmResponse.patch> patchRsData = ConfirmValidate.validateConfirmPatch(patchConfirmRequest);
        if (!patchRsData.getIsSuccess()) {
            // 검증 실패 시 해당 검증 실패 코드, 메시지 반환
            return RsData.of(
                    patchRsData.getRsCode(),
                    patchRsData.getMsg(),
                    patchRsData.getData()
            );
        }

        RsData<Confirm> confirmRsData = this.confirmService.updateConfirm(optionalConfirm.get(), patchConfirmRequest);

        return RsData.of(
                confirmRsData.getRsCode(),
                confirmRsData.getMsg(),
                new ConfirmResponse.patch(new ConfirmDTO(confirmRsData.getData()))
        );
    }

    @PatchMapping("/{confirmId}/change-status")
    public RsData<ConfirmResponse.changeStatus> changeStatusConfirm(@PathVariable(value = "confirmId") Long confirmId, @Valid @RequestBody ConfirmRequest.changeStatus changeStatusRequest){
        // 결재 처리상태 검증
        RsData<ConfirmResponse.patch> patchRsData = ConfirmValidate.validateConfirmStatusPatch(changeStatusRequest.getConfirmStatus());
        // {confirmId}번 결재 존재하는지 검증
        Optional<Confirm> optionalConfirm = this.confirmService.findById(confirmId);
        if(optionalConfirm.isEmpty()){
            return RsData.of(
                    RsCode.F_04,
                    "id: %d번 결재 는 존재하지 않습니다.".formatted(confirmId),
                    null
            );
        }

        RsData<Confirm> confirmRsData = this.confirmService.changeStatusConfirm(optionalConfirm.get(), changeStatusRequest);
        /// 처리 상태, 양식 별 메스드 추가 시 이곳에 작성

        ///
        return RsData.of(
                confirmRsData.getRsCode(),
                confirmRsData.getMsg(),
                new ConfirmResponse.changeStatus(new ConfirmDTO(confirmRsData.getData()))
        );
    }
}
