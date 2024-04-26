package com.app.businessBridge.domain.position.service;

import com.app.businessBridge.domain.position.entity.Position;
import com.app.businessBridge.domain.position.repository.PostionRepository;
import com.app.businessBridge.global.RsData.RsData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PositionService {
    private final PostionRepository postionRepository;

    public RsData<Position> create(Integer positionCode, String positionName) {
        Position position = Position.builder()
                .positionCode(positionCode)
                .positionName(positionName)
                .build();
        this.postionRepository.save(position);

        return RsData.of("S-1", "success", position);
    }
}
