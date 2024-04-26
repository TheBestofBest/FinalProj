package com.app.businessBridge.domain.position.service;

import com.app.businessBridge.domain.position.repository.PostionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PositionService {
    private final PostionRepository postionRepository;
}
