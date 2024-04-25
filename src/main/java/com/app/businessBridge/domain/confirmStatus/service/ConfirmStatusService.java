package com.app.businessBridge.domain.confirmStatus.service;

import com.app.businessBridge.domain.confirmStatus.repository.ConfirmStatusRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ConfirmStatusService {
    private final ConfirmStatusRepository confirmStatusRepository;
}
