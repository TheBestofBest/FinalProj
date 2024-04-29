package com.app.businessBridge.domain.confirmFormType.controller;

import com.app.businessBridge.domain.confirmFormType.dto.ConfirmFormTypeDTO;
import com.app.businessBridge.domain.confirmFormType.entity.ConfirmFormType;
import com.app.businessBridge.domain.confirmFormType.request.CreateConfirmFormTypeRequest;
import com.app.businessBridge.domain.confirmFormType.request.PatchConfirmFormTypeRequest;
import com.app.businessBridge.domain.confirmFormType.response.ConfirmFormTypesResponse;
import com.app.businessBridge.domain.confirmFormType.response.CreateConfirmFormTypeResponse;
import com.app.businessBridge.domain.confirmFormType.response.DeleteConfirmFormTypeResponse;
import com.app.businessBridge.domain.confirmFormType.response.PatchConfirmFormTypeResponse;
import com.app.businessBridge.domain.confirmFormType.service.ConfirmFormTypeService;
import com.app.businessBridge.domain.confirmStatus.dto.ConfirmStatusDTO;
import com.app.businessBridge.domain.confirmStatus.controller.ApiV1ConfirmStatusController;
import com.app.businessBridge.domain.confirmStatus.response.CreateConfirmStatusResponse;
import com.app.businessBridge.global.RsData.RsCode;
import com.app.businessBridge.global.RsData.RsData;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.sound.midi.Patch;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/confirm-form-types")
public class ApiV1ConfirmFormTypeController {
    private final ConfirmFormTypeService confirmFormTypeService;

    // 결재 양식 다건 조회
    @GetMapping("")
    public RsData<ConfirmFormTypesResponse> getConfirmFormTypes(){
        List<ConfirmFormType> confirmFormTypes = this.confirmFormTypeService.getAll();
        List<ConfirmFormTypeDTO> confirmFormTypeDTOS = new ArrayList<>();
        for (ConfirmFormType confirmFormType : confirmFormTypes) {
            confirmFormTypeDTOS.add(new ConfirmFormTypeDTO(confirmFormType));
        }

        return RsData.of(RsCode.S_01, "성공", new ConfirmFormTypesResponse(confirmFormTypeDTOS));
    }

    // 결재 양식 신규 등록
    @PostMapping("")
    public RsData<CreateConfirmFormTypeResponse> createConfirmFormType(@Valid @RequestBody CreateConfirmFormTypeRequest createConfirmFormTypeRequest){
        RsData<ConfirmFormType> confirmFormTypeRsData = this.confirmFormTypeService.create(createConfirmFormTypeRequest.getFormName(), createConfirmFormTypeRequest.getFormDescription());
        return RsData.of(
                confirmFormTypeRsData.getRsCode(),
                confirmFormTypeRsData.getMsg(),
                new CreateConfirmFormTypeResponse(new ConfirmFormTypeDTO(confirmFormTypeRsData.getData())));
    }

    // 결재 양식 수정
    @PatchMapping("/{ConfirmFormTypeId}")
    public RsData<PatchConfirmFormTypeResponse> patchConfirmFormType(@PathVariable(value = "ConfirmFormTypeId") Long confirmFormTypeId, @Valid @RequestBody PatchConfirmFormTypeRequest patchConfirmFormTypeRequest){
        Optional<ConfirmFormType> optionalConfirmFormType = this.confirmFormTypeService.getConfirmFormType(confirmFormTypeId);
        if (optionalConfirmFormType.isEmpty()){
            return RsData.of(
                    RsCode.F_04,
                    "%d번 결재 양식은 존재하지 않습니다.".formatted(confirmFormTypeId),
                    null
            );
        }

        RsData<ConfirmFormType> confirmFormTypeRsData = this.confirmFormTypeService.updateConfirmFormType(optionalConfirmFormType.get(),patchConfirmFormTypeRequest.getFormName(),patchConfirmFormTypeRequest.getFormDescription());

        return RsData.of(
                confirmFormTypeRsData.getRsCode(),
                confirmFormTypeRsData.getMsg(),
                new PatchConfirmFormTypeResponse(new ConfirmFormTypeDTO(confirmFormTypeRsData.getData()))
        );
    }

    // 결재 양식 삭제
    @DeleteMapping("/{ConfirmFormTypeId}")
    public RsData<DeleteConfirmFormTypeResponse> deleteConfirmFormType(@PathVariable(value = "ConfirmFormTypeId") Long confirmFormTypeId){
        Optional<ConfirmFormType> optionalConfirmFormType = this.confirmFormTypeService.getConfirmFormType(confirmFormTypeId);
        if (optionalConfirmFormType.isEmpty()){
            return RsData.of(
                    RsCode.F_04,
                    "%d번 결재 양식은 존재하지 않습니다.".formatted(confirmFormTypeId),
                    null
            );
        }
        this.confirmFormTypeService.deleteConfirmFormType(optionalConfirmFormType.get());

        return RsData.of(
                RsCode.S_04,
                "%d번 결재 양식이 삭제되었습니다.",
                new DeleteConfirmFormTypeResponse(confirmFormTypeId)
        ) ;
    }
}
