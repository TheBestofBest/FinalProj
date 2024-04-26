package com.app.businessBridge.domain.rebate.service;


import com.app.businessBridge.domain.rebate.repository.RebateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RebateService {

    private final RebateRepository rebateRepository;
}
