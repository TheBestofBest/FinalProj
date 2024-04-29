package com.app.businessBridge.domain.confirm.service;

import com.app.businessBridge.domain.confirm.entity.Confirm;
import com.app.businessBridge.domain.confirm.repository.ConfirmRepository;
import com.app.businessBridge.domain.confirm.request.ConfirmRequest;
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

    }
}
