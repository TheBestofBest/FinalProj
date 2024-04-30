package com.app.businessBridge.domain.confirm.validation;


import com.app.businessBridge.domain.confirm.response.ConfirmResponse;
import com.app.businessBridge.domain.confirmFormType.entity.ConfirmFormType;
import com.app.businessBridge.domain.confirmFormType.service.ConfirmFormTypeService;
import com.app.businessBridge.global.RsData.RsCode;
import com.app.businessBridge.global.RsData.RsData;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import java.util.Optional;

@Getter
@RequiredArgsConstructor
public class ConfirmValidate {
    private static ConfirmFormTypeService confirmFormTypeService;

    // 결재 시 결재 양식, 처리상태, 결재 요청자, 결재 승인자 검증 메서드
    public static RsData<ConfirmResponse.create> validateConfirmFormType(ConfirmFormType formType){
        // 결재 양식 검증
        Optional<ConfirmFormType> optionalConfirmFormType = confirmFormTypeService.getConfirmFormType(formType.getId());
        // null 이면 실패코드 반환
        if(optionalConfirmFormType.isEmpty()){
            return RsData.of(
                    RsCode.F_04,
                    "해당 양식은 존재하지 않습니다.",
                    null
            );
        }
        //검증 통과 시 성공코드 리턴
        return RsData.of(
                RsCode.S_08,
                "결재 양식 검증됨",
                null
        );
    }
}
