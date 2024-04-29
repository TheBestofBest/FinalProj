package com.app.businessBridge.domain.confirmStatus.service;

import com.app.businessBridge.domain.confirmStatus.entity.ConfirmStatus;
import com.app.businessBridge.domain.confirmStatus.repository.ConfirmStatusRepository;
import com.app.businessBridge.global.RsData.RsCode;
import com.app.businessBridge.global.RsData.RsData;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ConfirmStatusService {
    private final ConfirmStatusRepository confirmStatusRepository;

    public List<ConfirmStatus> getAll() {
        return this.confirmStatusRepository.findAll();
    }

    @Transactional
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
}
