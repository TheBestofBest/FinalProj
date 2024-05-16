package com.app.businessBridge.domain.confirm.service;

import com.app.businessBridge.domain.confirm.entity.Confirm;
import com.app.businessBridge.domain.confirm.repository.ConfirmRepository;
import com.app.businessBridge.domain.confirm.request.ConfirmRequest;
import com.app.businessBridge.domain.confirmStatus.entity.ConfirmStatus;
import com.app.businessBridge.domain.member.entity.Member;
import com.app.businessBridge.global.RsData.RsCode;
import com.app.businessBridge.global.RsData.RsData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ConfirmService {
    private final ConfirmRepository confirmRepository;

    public List<Confirm> getAll() {
        return this.confirmRepository.findAll();
    }

    public Optional<Confirm> findById(Long confirmId) {
        return this.confirmRepository.findById(confirmId);
    }

    public RsData<Confirm> createConfirm(ConfirmRequest.create createConfirmRequest, ConfirmStatus confirmStatus, Member confirmRequestMember, List<Member> confirmMembers) {
        Confirm confirm = Confirm.builder()
                .subject(createConfirmRequest.getSubject())
                .description(createConfirmRequest.getDescription())
                .formData(createConfirmRequest.getFormData())
                .formType(createConfirmRequest.getFormType())
                .confirmStatus(confirmStatus)
                .confirmRequestMember(confirmRequestMember)
                .confirmMembers(confirmMembers)
                .build();
        this.confirmRepository.save(confirm);

        return RsData.of(
                RsCode.S_02,
                "결재 등록이 완료되었습니다.",
                confirm
        );
    }

//    public RsData<Confirm> updateConfirm(Confirm confirm, ConfirmRequest.patch patchConfirmRequest) {
//        Confirm patchedConfirm = confirm.toBuilder()
//                .subject(patchConfirmRequest.getSubject())
//                .description(patchConfirmRequest.getDescription())
//                .formData(patchConfirmRequest.getFormData())
//                .formType(patchConfirmRequest.getFormType())
//                .confirmStatus(patchConfirmRequest.getConfirmStatus())
//                .confirmRequestMember(patchConfirmRequest.getConfirmRequestMember())
//                .confirmMembers(patchConfirmRequest.getConfirmMembers())
//                .build();
//        this.confirmRepository.save(patchedConfirm);
//
//        return RsData.of(
//                RsCode.S_03,
//                "%d번 결재 수정이 완료되었습니다.",
//                patchedConfirm
//        );
//    }

    public RsData<Confirm> changeStatusConfirm(Confirm confirm, ConfirmRequest.changeStatus changeStatusRequest) {
        Confirm statusChangedConfirm = confirm.toBuilder()
                .review(changeStatusRequest.getReview())
                .confirmStatus(changeStatusRequest.getConfirmStatus())
                .build();

        this.confirmRepository.save(statusChangedConfirm);

        return RsData.of(
                RsCode.S_03,
                "%d번 결재 처리상태가 %s(으)로 변경되었습니다.".formatted(confirm.getId(), changeStatusRequest.getConfirmStatus().getStatusName()),
                statusChangedConfirm
        );
    }

    public void deleteConfirm(Confirm confirm) {
        this.confirmRepository.delete(confirm);
    }

    public RsData<Confirm> changeCounter(Confirm confirm) {
        Confirm confirm1 = confirm.toBuilder()
                .confirmStepCounter(confirm.getConfirmStepCounter()+1)
                .build();
        this.confirmRepository.save(confirm1);
        return RsData.of(RsCode.S_03,"승인 확인.", confirm1 );
    }

    public RsData<Confirm> confirmConfirm(Confirm data, ConfirmStatus confirmStatus) {
        Confirm confirm = data.toBuilder()
                .confirmStatus(confirmStatus)
                .build();
        this.confirmRepository.save(confirm);

        return RsData.of(RsCode.S_03, "승인 처리됨", confirm);
    }
}
