package com.app.businessBridge.domain.confirm.dto;

import com.app.businessBridge.domain.confirm.entity.Confirm;
import com.app.businessBridge.domain.confirmFormType.dto.ConfirmFormTypeDTO;
import com.app.businessBridge.domain.confirmFormType.entity.ConfirmFormType;
import com.app.businessBridge.domain.confirmStatus.dto.ConfirmStatusDTO;
import com.app.businessBridge.domain.confirmStatus.entity.ConfirmStatus;
import com.app.businessBridge.domain.member.entity.Member;
import jakarta.persistence.Column;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
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
    private String content;
    // 결재 등록일
    private LocalDateTime createDate;
    // 결재 수정일
    private LocalDateTime modifiedDate;
    // 결재 양식 타입(휴가승인서, 보고서 등)
    private ConfirmFormTypeDTO formTypeDTO;
    // 결재 처리 상태(진행중, 승인, 반려 등)
    private ConfirmStatusDTO confirmStatusDTO;

    // 결재 요청자
    private Member confirmRequestMember;
    // 결재 승인자
    private List<Member> confirmMembers;

    public ConfirmDTO(Confirm confirm) {
        this.id = confirm.getId();
        this.subject = confirm.getSubject();
        this.description = confirm.getDescription();
        this.content = confirm.getContent();
        this.createDate = confirm.getCreatedDate();
        this.modifiedDate = confirm.getModifiedDate();
        this.formTypeDTO = new ConfirmFormTypeDTO(confirm.getFormType());
        this.confirmStatusDTO = new ConfirmStatusDTO(confirm.getConfirmStatus());
        // ! memberDTO 추가되면 DTO 생성자 추가하기 !
        this.confirmRequestMember = confirm.getConfirmRequestMember();
        this.confirmMembers = confirm.getConfirmMembers();
    }
}
