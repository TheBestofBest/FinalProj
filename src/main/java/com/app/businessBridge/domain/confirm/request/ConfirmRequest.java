package com.app.businessBridge.domain.confirm.request;

import com.app.businessBridge.domain.confirmFormType.entity.ConfirmFormType;
import com.app.businessBridge.domain.confirmStatus.entity.ConfirmStatus;
import com.app.businessBridge.domain.member.entity.Member;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class ConfirmRequest {

    @Getter
    @Setter
    public static class create{
        // 결재 제목
        private String subject;
        // 결재 간략설명
        private String description;
        // 결재 상세내용
        private String formData;

        // 결재 양식 타입(휴가승인서, 보고서 등)
        private ConfirmFormType formType;
        // 결재 처리 상태(진행중, 승인, 반려 등)
        private ConfirmStatus confirmStatus;

        // 결재 요청자
        private Member confirmRequestMember;
        // 결재 승인자
        private List<Member> confirmMembers;
    }

    @Getter
    @Setter
    public static class patch{
        // 결재 제목
        private String subject;
        // 결재 간략설명
        private String description;
        // 결재 상세내용
        private String formData;

        // 결재 양식 타입(휴가승인서, 보고서 등)
        private ConfirmFormType formType;
        // 결재 처리 상태(진행중, 승인, 반려 등)
        private ConfirmStatus confirmStatus;

        // 결재 요청자
        private Member confirmRequestMember;
        // 결재 승인자
        private List<Member> confirmMembers;
    }

    @Getter
    @Setter
    public static class changeStatus{
        // 결재 리뷰(승인자가 남기는 리뷰)
        private String review;
        // 결재 처리 상태(진행중, 승인, 반려 등)
        private ConfirmStatus confirmStatus;
    }

}
