package com.app.businessBridge.domain.confirm.service;

import com.app.businessBridge.domain.confirm.entity.Confirm;
import com.app.businessBridge.domain.confirm.repository.ConfirmRepository;
import com.app.businessBridge.domain.confirm.request.ConfirmRequest;
import com.app.businessBridge.global.RsData.RsCode;
import com.app.businessBridge.global.RsData.RsData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ConfirmService {
    private final ConfirmRepository confirmRepository;

    public List<Confirm> getAll() {
        return this.confirmRepository.findAll();
    }

    public RsData<Confirm> createConfirm(ConfirmRequest.create createConfirmRequest) {
        Confirm confirm = Confirm.builder()
                .subject(createConfirmRequest.getSubject())
                .description(createConfirmRequest.getDescription())
                .content(createConfirmRequest.getContent())
                .formType(createConfirmRequest.getFormType())
                .confirmStatus(createConfirmRequest.getConfirmStatus())
                .confirmRequestMember(createConfirmRequest.getConfirmRequestMember())
                .confirmMembers(createConfirmRequest.getConfirmMembers())
                .build();
        this.confirmRepository.save(confirm);

        return RsData.of(
                RsCode.S_02,
                "결재 등록이 완료되었습니다.",
                confirm
        );
    }
}
