package com.app.businessBridge.domain.confirm.service;

import com.app.businessBridge.domain.confirm.repository.ConfirmRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ConfirmService {
    private final ConfirmRepository confirmRepository;
}
