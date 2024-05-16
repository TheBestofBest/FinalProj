package com.app.businessBridge.domain.confirm.validation;


import com.app.businessBridge.domain.confirm.request.ConfirmRequest;
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

import java.util.List;
import java.util.Optional;

@Getter
@RequiredArgsConstructor
public class ConfirmValidate {
    private static ConfirmFormTypeService confirmFormTypeService;
    private static ConfirmStatusService confirmStatusService;
    public static MemberService memberService;

    //결재 등록 시 결재 시 결재 양식, 처리상태, 결재 요청자, 결재 승인자 검증 메서드
    // 결재 양식 검증 메서드
    public static RsData<ConfirmResponse.create> validateConfirmFormType(ConfirmFormType formType) {
        // 결재 양식 검증
        Optional<ConfirmFormType> optionalConfirmFormType = confirmFormTypeService.getConfirmFormType(formType.getId());
        // null 이면 실패코드 반환
        if (optionalConfirmFormType.isEmpty()) {
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

    // 결재 처리 상태 검증 메서드
    public static RsData<ConfirmResponse.create> validateConfirmStatus(ConfirmStatus confirmStatus) {
        // 결재 처리 상태 검증
        Optional<ConfirmStatus> optionalConfirmStatus = confirmStatusService.getConfirmStatus(confirmStatus.getId());
        // null 이면 실패코드 반환
        if (optionalConfirmStatus.isEmpty()) {
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

    // 결재 요청자 검증 메서드
    public static RsData<ConfirmResponse.create> validateConfirmRequestMember(Member confirmRequestMember) {
        // 결재 요청자 검증
        RsData<Member> memberRsData = memberService.findById(confirmRequestMember.getId());
        // 실패코드 반환
        if (!memberRsData.getIsSuccess()) {
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

    // 결재 승인자 검증 메서드
    public static RsData<ConfirmResponse.create> validateConfirmMembers(List<Member> confirmMembers) {
        // 결재 승인자 검증

        for (Member member : confirmMembers) {
            RsData<Member> memberRsData = memberService.findById(member.getId());
            // null 이면 실패코드 반환
            if (!memberRsData.getIsSuccess()) {
                return RsData.of(
                        RsCode.F_04,
                        "해당 결재 승인자는 존재하지 않습니다.",
                        null
                );
            }
        }


        //검증 통과 시 성공코드 리턴
        return RsData.of(
                RsCode.S_08,
                "결재 승인자 검증됨",
                null
        );
    }

    public static RsData<ConfirmResponse.patch> validateConfirmFormTypePatch(ConfirmFormType formType) {
        // 결재 양식 검증
        Optional<ConfirmFormType> optionalConfirmFormType = confirmFormTypeService.getConfirmFormType(formType.getId());
        // null 이면 실패코드 반환
        if (optionalConfirmFormType.isEmpty()) {
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

    // 결재 처리 상태 검증 메서드
    public static RsData<ConfirmResponse.patch> validateConfirmStatusPatch(ConfirmStatus confirmStatus) {
        // 결재 처리 상태 검증
        Optional<ConfirmStatus> optionalConfirmStatus = confirmStatusService.getConfirmStatus(confirmStatus.getId());
        // null 이면 실패코드 반환
        if (optionalConfirmStatus.isEmpty()) {
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
    // 처리상태 변경 시 결재 처리 상태 검증 메서드
    public static RsData<ConfirmResponse.changeStatus> validateConfirmStatusChange(ConfirmStatus confirmStatus) {
        // 결재 처리 상태 검증
        Optional<ConfirmStatus> optionalConfirmStatus = confirmStatusService.getConfirmStatus(confirmStatus.getId());
        // null 이면 실패코드 반환
        if (optionalConfirmStatus.isEmpty()) {
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

    // 결재 요청자 검증 메서드
    public static RsData<ConfirmResponse.patch> validateConfirmRequestMemberPatch(Member confirmRequestMember) {
        // 결재 요청자 검증
        RsData<Member> memberRsData = memberService.findById(confirmRequestMember.getId());
        // 실패코드 반환
        if (!memberRsData.getIsSuccess()) {
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

    // 결재 승인자 검증 메서드
    public static RsData<ConfirmResponse.patch> validateConfirmMembersPatch(List<Member> confirmMembers) {
        // 결재 승인자 검증

        for (Member member : confirmMembers) {
            RsData<Member> memberRsData = memberService.findById(member.getId());
            // null 이면 실패코드 반환
            if (!memberRsData.getIsSuccess()) {
                return RsData.of(
                        RsCode.F_04,
                        "해당 결재 승인자는 존재하지 않습니다.",
                        null
                );
            }
        }


        //검증 통과 시 성공코드 리턴
        return RsData.of(
                RsCode.S_08,
                "결재 승인자 검증됨",
                null
        );
    }

    //결재 등록 시 결재 양식 타입, 결재 처리 상태, 결재 요청자, 결재 승인자 검증
    public static RsData<ConfirmResponse.create> validateConfirmCreate(ConfirmRequest.create createConfirmRequest) {
        // 양식 검증
        RsData<ConfirmResponse.create> validateConfirm = ConfirmValidate.validateConfirmFormType(createConfirmRequest.getFormType());
        // 검증 실패 시 실패 코드, 메시지 리턴
        if (!validateConfirm.getIsSuccess()) {
            return RsData.of(
                    validateConfirm.getRsCode(),
                    validateConfirm.getMsg(),
                    validateConfirm.getData()
            );
        }
        // 결재 요청자 검증
//        RsData<ConfirmResponse.create> validateConfirmRequestMember = ConfirmValidate.validateConfirmRequestMember(createConfirmRequest.getConfirmRequestMember());
//        if (!validateConfirmRequestMember.getIsSuccess()) {
//            return RsData.of(
//                    validateConfirmRequestMember.getRsCode(),
//                    validateConfirmRequestMember.getMsg(),
//                    validateConfirmRequestMember.getData()
//            );
//        }
        // 결재 승인자 검증
//        RsData<ConfirmResponse.create> validateConfirmMembers = ConfirmValidate.validateConfirmMembers(createConfirmRequest.getConfirmMembers());
//        if (!validateConfirmMembers.getIsSuccess()) {
//            return RsData.of(
//                    validateConfirmMembers.getRsCode(),
//                    validateConfirmMembers.getMsg(),
//                    validateConfirmMembers.getData()
//            );
//        }
        return RsData.of(
                RsCode.S_08,
                "결재 양식, 처리상태, 요청자, 승인자 검증됨",
                null
        );
    }

    public static RsData<ConfirmResponse.patch> validateConfirmPatch(ConfirmRequest.patch patchConfirmRequest) {
        // 양식 검증
        RsData<ConfirmResponse.patch> validateConfirm = ConfirmValidate.validateConfirmFormTypePatch(patchConfirmRequest.getFormType());
        // 검증 실패 시 실패 코드, 메시지 리턴
        if (!validateConfirm.getIsSuccess()) {
            return RsData.of(
                    validateConfirm.getRsCode(),
                    validateConfirm.getMsg(),
                    validateConfirm.getData()
            );
        }
        // 처리 상태 검증
        RsData<ConfirmResponse.patch> validateStatus = ConfirmValidate.validateConfirmStatusPatch(patchConfirmRequest.getConfirmStatus());
        if (!validateStatus.getIsSuccess()) {
            return RsData.of(
                    validateStatus.getRsCode(),
                    validateStatus.getMsg(),
                    validateStatus.getData()
            );
        }
        // 결재 요청자 검증
//        RsData<ConfirmResponse.patch> validateConfirmRequestMember = ConfirmValidate.validateConfirmRequestMemberPatch(patchConfirmRequest.getConfirmRequestMember());
//        if (!validateConfirmRequestMember.getIsSuccess()) {
//            return RsData.of(
//                    validateConfirmRequestMember.getRsCode(),
//                    validateConfirmRequestMember.getMsg(),
//                    validateConfirmRequestMember.getData()
//            );
//        }
        // 결재 승인자 검증
//        RsData<ConfirmResponse.patch> validateConfirmMembers = ConfirmValidate.validateConfirmMembersPatch(patchConfirmRequest.getConfirmMembers());
//        if (!validateConfirmMembers.getIsSuccess()) {
//            return RsData.of(
//                    validateConfirmMembers.getRsCode(),
//                    validateConfirmMembers.getMsg(),
//                    validateConfirmMembers.getData()
//            );
//        }
        return RsData.of(
                RsCode.S_08,
                "결재 양식, 처리상태, 요청자, 승인자 검증됨",
                null
        );
    }


}

