package com.app.businessBridge.domain.confirm.validation;


import com.app.businessBridge.domain.confirm.entity.Confirm;
import com.app.businessBridge.domain.confirm.response.ConfirmResponse;
import com.app.businessBridge.domain.confirmFormType.entity.ConfirmFormType;
import com.app.businessBridge.domain.confirmFormType.service.ConfirmFormTypeService;
import com.app.businessBridge.domain.confirmStatus.entity.ConfirmStatus;
import com.app.businessBridge.domain.confirmStatus.service.ConfirmStatusService;
import com.app.businessBridge.domain.member.Service.MemberService;
import com.app.businessBridge.domain.member.entity.Member;
import com.app.businessBridge.global.RsData.RsCode;
import com.app.businessBridge.global.RsData.RsData;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import java.util.Optional;

@Getter
@RequiredArgsConstructor
public class ConfirmValidate {
    private static ConfirmFormTypeService confirmFormTypeService;
    private static ConfirmStatusService confirmStatusService;
    public static MemberService memberService;

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
    public static RsData<ConfirmResponse.create> validateConfirmStatus(ConfirmStatus confirmStatus){
        // 결재 양식 검증
        Optional<ConfirmStatus> optionalConfirmStatus = confirmStatusService.getConfirmStatus(confirmStatus.getId());
        // null 이면 실패코드 반환
        if(optionalConfirmStatus.isEmpty()){
            return RsData.of(
                    RsCode.F_04,
                    "해당 결재 처리상태는 존재하지 않습니다.",
                    null
            );
        }
        //검증 통과 시 성공코드 리턴
        return RsData.of(
                RsCode.S_08,
                "결재 처리상태 검증됨",
                null
        );
    }
    public static RsData<ConfirmResponse.create> validateConfirmRequestMember(Member confirmRequestMember){
        // 결재 요청자 검증
        RsData<Member> memberRsData = memberService.findById(confirmRequestMember.getId());
        // 실패코드 반환
        if(!memberRsData.getIsSuccess()){
            return RsData.of(
                    RsCode.F_04,
                    "해당 결재 요청자는 존재하지 않습니다.",
                    null
            );
        }
        //검증 통과 시 성공코드 리턴
        return RsData.of(
                RsCode.S_08,
                "결재 요청자 검증됨",
                null
        );
    }
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
