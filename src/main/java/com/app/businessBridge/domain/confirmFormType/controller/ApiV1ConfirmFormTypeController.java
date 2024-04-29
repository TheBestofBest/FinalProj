package com.app.businessBridge.domain.confirmFormType.controller;

import com.app.businessBridge.domain.confirmFormType.dto.ConfirmFormTypeDTO;
import com.app.businessBridge.domain.confirmFormType.entity.ConfirmFormType;
import com.app.businessBridge.domain.confirmFormType.request.ConfirmFormTypeRequest;
import com.app.businessBridge.domain.confirmFormType.response.ConfirmFormTypeResponse;
import com.app.businessBridge.domain.confirmFormType.service.ConfirmFormTypeService;
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
@RequestMapping("/api/v1/confirm-form-types")
public class ApiV1ConfirmFormTypeController {
    private final ConfirmFormTypeService confirmFormTypeService;

    // 결재 양식 다건 조회
    @GetMapping("")
    public RsData<ConfirmFormTypeResponse.getAll> getConfirmFormTypes(){
        List<ConfirmFormType> confirmFormTypes = this.confirmFormTypeService.getAll();
        List<ConfirmFormTypeDTO> confirmFormTypeDTOS = new ArrayList<>();
        for (ConfirmFormType confirmFormType : confirmFormTypes) {
            confirmFormTypeDTOS.add(new ConfirmFormTypeDTO(confirmFormType));
        }

        return RsData.of(RsCode.S_01, "성공", new ConfirmFormTypeResponse.getAll(confirmFormTypeDTOS));
    }

    // 결재 양식 신규 등록
    @PostMapping("")
    public RsData<ConfirmFormTypeResponse.create> createConfirmFormType(@Valid @RequestBody ConfirmFormTypeRequest.create createConfirmFormTypeRequest){
        RsData<ConfirmFormType> confirmFormTypeRsData = this.confirmFormTypeService.create(createConfirmFormTypeRequest.getFormName(), createConfirmFormTypeRequest.getFormDescription());
        return RsData.of(
                confirmFormTypeRsData.getRsCode(),
                confirmFormTypeRsData.getMsg(),
                new ConfirmFormTypeResponse.create(new ConfirmFormTypeDTO(confirmFormTypeRsData.getData())));
    }

    // 결재 양식 수정
    @PatchMapping("/{ConfirmFormTypeId}")
    public RsData<ConfirmFormTypeResponse.patch> patchConfirmFormType(@PathVariable(value = "ConfirmFormTypeId") Long confirmFormTypeId, @Valid @RequestBody ConfirmFormTypeRequest.patch patchConfirmFormTypeRequest){
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
                new ConfirmFormTypeResponse.patch(new ConfirmFormTypeDTO(confirmFormTypeRsData.getData()))
        );
    }

    // 결재 양식 삭제
    @DeleteMapping("/{ConfirmFormTypeId}")
    public RsData<ConfirmFormTypeResponse.delete> deleteConfirmFormType(@PathVariable(value = "ConfirmFormTypeId") Long confirmFormTypeId){
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
                new ConfirmFormTypeResponse.delete(confirmFormTypeId)
        ) ;
    }
}
