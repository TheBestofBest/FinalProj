package com.app.businessBridge.domain.confirmStatus.service;

import com.app.businessBridge.domain.confirmStatus.entity.ConfirmStatus;
import com.app.businessBridge.domain.confirmStatus.repository.ConfirmStatusRepository;
import com.app.businessBridge.global.RsData.RsCode;
import com.app.businessBridge.global.RsData.RsData;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ConfirmStatusService {
    private final ConfirmStatusRepository confirmStatusRepository;

    public List<ConfirmStatus> getAll() {
        return this.confirmStatusRepository.findAll();
    }

    public RsData<ConfirmStatus> create(String statusName, String statusDescription) {
        ConfirmStatus confirmStatus = ConfirmStatus.builder()
                .statusName(statusName)
                .statusDescription(statusDescription)
                .build();
        this.confirmStatusRepository.save(confirmStatus);
        return RsData.of(
                RsCode.S_02,
                "새로운 처리상태가 성공적으로 생성되었습니다.",
                confirmStatus
                );
    }

    public Optional<ConfirmStatus> getConfirmStatus(Long confirmStatusId) {

        return this.confirmStatusRepository.findById(confirmStatusId);
    }

    public RsData<ConfirmStatus> updateConfirmStatus(ConfirmStatus confirmStatus, String statusName, String statusDescription) {
        ConfirmStatus updatedConfirmStatus = confirmStatus.toBuilder()
                .statusName(statusName)
                .statusDescription(statusDescription)
                .build();
        this.confirmStatusRepository.save(updatedConfirmStatus);

        return RsData.of(
                RsCode.S_03,
                "%d번 결재 처리 상태 수정이 성공했습니다.".formatted(confirmStatus.getId()),
                updatedConfirmStatus
        );
    }

    public void deleteConfirmStatus(ConfirmStatus confirmStatus) {
        this.confirmStatusRepository.delete(confirmStatus);
    }
}
