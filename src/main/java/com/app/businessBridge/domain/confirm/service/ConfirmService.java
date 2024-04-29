package com.app.businessBridge.domain.confirm.service;

import com.app.businessBridge.domain.confirm.entity.Confirm;
import com.app.businessBridge.domain.confirm.repository.ConfirmRepository;
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
}
