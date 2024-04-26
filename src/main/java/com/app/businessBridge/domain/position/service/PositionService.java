package com.app.businessBridge.domain.position.service;

import com.app.businessBridge.domain.position.entity.Position;
import com.app.businessBridge.domain.position.repository.PostionRepository;
import com.app.businessBridge.global.RsData.RsData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PositionService {
    private final PostionRepository postionRepository;

    // 직급 생성
    public RsData<Position> create(Integer positionCode, String positionName) {
        Position position = Position.builder()
                .positionCode(positionCode)
                .positionName(positionName)
                .build();
        this.postionRepository.save(position);

        return RsData.of("S-02", "새로운 리소스가 성공적으로 생성되었습니다.", position);
    }

    // 직급 모두 불러오기
    public RsData<List<Position>> read() {
        List<Position> positionList = this.postionRepository.findAll();
        return RsData.of("S-05", "요청한 리소스를 찾았습니다.", positionList);
    }

    // 직급 업데이트
    public RsData<Position> update(Position position, Integer positionCode, String positionName) {
        position.setPositionCode(positionCode);
        position.setPositionName(positionName);
        this.postionRepository.save(position);

        return RsData.of("S-03", "리소스가 성공적으로 업데이트되었습니다.", position);
    }

    // 직급 삭제
    public RsData delete(Long id) {
        Optional<Position> op = findById(id);
        if (op.isEmpty()) {
            return RsData.of("F-04", "요청한 리소스를 찾을 수 없습니다.");
        }

        this.postionRepository.delete(op.get());
        return RsData.of("S-04", "리소스가 성공적으로 삭제되었습니다.");
    }

    // 직급 단건 찾기 Optional
    public Optional<Position> findById(Long id) {
        return this.postionRepository.findById(id);
    }
}
