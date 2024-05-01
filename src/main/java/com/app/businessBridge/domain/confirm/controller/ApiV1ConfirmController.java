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
import lombok.Value;
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
    public RsData<ConfirmResponse.getConfirms> getConfirms() {
        List<Confirm> confirms = this.confirmService.getAll();

        return RsData.of(
                RsCode.S_01,
                "결재 다건 조회 성공",
                new ConfirmResponse.getConfirms(confirms)
        );
    }

    // 결재 단건 조회
    @GetMapping("/{id}")
    public RsData<ConfirmResponse.getConfirm> getConfirm(@PathVariable(value = "id") Long id){
        Optional<Confirm> optionalConfirm = this.confirmService.findById(id);
        if(optionalConfirm.isEmpty()){
            return RsData.of(
                    RsCode.F_04,
                    "id: %d번 결재 는 존재하지 않습니다.".formatted(id),
                    null
            );
        }
        return RsData.of(
                RsCode.S_05,
                "id: %d번 결재 단건 조회 성공".formatted(id),
                new ConfirmResponse.getConfirm(optionalConfirm.get())
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
                new ConfirmResponse.create(confirmRsData.getData())
        );
    }

    @PatchMapping("/{id}")
    public RsData<ConfirmResponse.patch> patchConfirm(@PathVariable(value="id") Long id ,@Valid @RequestBody ConfirmRequest.patch patchConfirmRequest) {
        Optional<Confirm> optionalConfirm = this.confirmService.findById(id);
        if(optionalConfirm.isEmpty()){
            return RsData.of(
                    RsCode.F_04,
                    "id: %d번 결재 는 존재하지 않습니다.".formatted(id),
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
                new ConfirmResponse.patch(confirmRsData.getData())
        );
    }

    @PatchMapping("/{id}/change-status")
    public RsData<ConfirmResponse.changeStatus> changeStatusConfirm(@PathVariable(value = "id") Long id, @Valid @RequestBody ConfirmRequest.changeStatus changeStatusRequest){
        // 결재 처리상태 검증
        RsData<ConfirmResponse.changeStatus> patchRsData = ConfirmValidate.validateConfirmStatusChange(changeStatusRequest.getConfirmStatus());
        if(!patchRsData.getIsSuccess()){
            return RsData.of(
                    patchRsData.getRsCode(),
                    patchRsData.getMsg(),
                    patchRsData.getData()
            );
        }
        // {id}번 결재 존재하는지 검증
        Optional<Confirm> optionalConfirm = this.confirmService.findById(id);
        if(optionalConfirm.isEmpty()){
            return RsData.of(
                    RsCode.F_04,
                    "id: %d번 결재 는 존재하지 않습니다.".formatted(id),
                    null
            );
        }

        RsData<Confirm> confirmRsData = this.confirmService.changeStatusConfirm(optionalConfirm.get(), changeStatusRequest);
        /// 처리 상태, 양식 별 메스드 추가 시 이곳에 작성

        ///
        return RsData.of(
                confirmRsData.getRsCode(),
                confirmRsData.getMsg(),
                new ConfirmResponse.changeStatus(confirmRsData.getData())
        );
    }

    @DeleteMapping("/{id}")
    public RsData<ConfirmResponse.delete> deleteConfirm(@PathVariable(value = "id") Long id){
        // {id}번 결재 존재하는지 검증
        Optional<Confirm> optionalConfirm = this.confirmService.findById(id);
        if(optionalConfirm.isEmpty()){
            return RsData.of(
                    RsCode.F_04,
                    "id: %d번 결재 는 존재하지 않습니다.".formatted(id),
                    null
            );
        }
        this.confirmService.deleteConfirm(optionalConfirm.get());
        return RsData.of(
                RsCode.S_04,
                "id: %d번 결재가 삭제되었습니다.".formatted(id),
                new ConfirmResponse.delete(id)
        );
    }
}
