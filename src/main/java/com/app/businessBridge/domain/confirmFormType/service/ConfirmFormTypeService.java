package com.app.businessBridge.domain.confirmFormType.service;

import com.app.businessBridge.domain.confirmFormType.entity.ConfirmFormType;
import com.app.businessBridge.domain.confirmFormType.repository.ConfirmFormTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ConfirmFormTypeService {
    private final ConfirmFormTypeRepository confirmFormTypeRepository;

    public List<ConfirmFormType> getAll() {
        return this.confirmFormTypeRepository.findAll();
    }
}
