package com.app.businessBridge.domain.confirmStatus.service;

import com.app.businessBridge.domain.confirmStatus.entity.ConfirmStatus;
import com.app.businessBridge.domain.confirmStatus.repository.ConfirmStatusRepository;
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
}
