package com.app.businessBridge.domain.confirm.dto;

import com.app.businessBridge.domain.confirm.entity.Confirm;
import com.app.businessBridge.domain.confirmFormType.dto.ConfirmFormTypeDTO;
import com.app.businessBridge.domain.confirmStatus.dto.ConfirmStatusDTO;
import com.app.businessBridge.domain.member.DTO.MemberDTO;
import com.app.businessBridge.domain.member.entity.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
public class ConfirmDTO {

    // 결재 id
    private Long id;
    // 결재 제목
    private String subject;
    // 결재 간략설명
    private String description;
    // 결재 상세내용
    private String formData;
    // 결재 승인자가 남기는 리뷰
    private String review;
    // 결재 등록일
    private LocalDateTime createDate;
    // 결재 수정일
    private LocalDateTime modifiedDate;
    // 결재 양식 타입(휴가승인서, 보고서 등)
    private ConfirmFormTypeDTO formTypeDTO;
    // 결재 처리 상태(진행중, 승인, 반려 등)
    private ConfirmStatusDTO confirmStatusDTO;
    private Long confirmStepCounter;

    // 결재 요청자
    private MemberDTO confirmRequestMember;
    // 결재 승인자
    private List<MemberDTO> confirmMembers;

    public ConfirmDTO(Confirm confirm) {
        this.id = confirm.getId();
        this.subject = confirm.getSubject();
        this.description = confirm.getDescription();
        this.formData = confirm.getFormData();
        this.review = confirm.getReview();
        this.createDate = confirm.getCreatedDate();
        this.modifiedDate = confirm.getModifiedDate();
        this.formTypeDTO = new ConfirmFormTypeDTO(confirm.getFormType());
        this.confirmStatusDTO = new ConfirmStatusDTO(confirm.getConfirmStatus());
        this.confirmRequestMember = new MemberDTO(confirm.getConfirmRequestMember());
        this.confirmMembers = confirm.getConfirmMembers().stream().map(MemberDTO::new).toList();
        this.confirmStepCounter = confirm.getConfirmStepCounter();
    }
}
