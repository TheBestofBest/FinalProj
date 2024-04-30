package com.app.businessBridge.domain.confirmStatus.service;

import com.app.businessBridge.domain.confirmStatus.entity.ConfirmStatus;
import com.app.businessBridge.domain.confirmStatus.repository.ConfirmStatusRepository;
import com.app.businessBridge.global.RsData.RsData;
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

//    public RsData<ConfirmStatus> create(String statusName, String formDescription) {
//        ConfirmStatus confirmStatus = ConfirmStatus.builder()
//                .statusName(statusName)
//                .formDescription(formDescription)
//                .build();
//        this.confirmStatusRepository.save(confirmStatus);
//        return RsData.of(
//                "S-02",
//                "새로운 처리상태가 성공적으로 생성되었습니다.",
//                confirmStatus
//                );
//    }
}
