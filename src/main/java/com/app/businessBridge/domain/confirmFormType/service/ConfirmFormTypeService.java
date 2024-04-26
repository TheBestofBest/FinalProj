package com.app.businessBridge.domain.confirmFormType.service;

import com.app.businessBridge.domain.confirmFormType.repository.ConfirmFormTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ConfirmFormTypeService {
    private final ConfirmFormTypeRepository confirmFormTypeRepository;
}
