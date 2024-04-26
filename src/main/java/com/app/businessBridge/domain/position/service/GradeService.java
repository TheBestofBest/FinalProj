package com.app.businessBridge.domain.position.service;

import com.app.businessBridge.domain.position.entity.Grade;
import com.app.businessBridge.domain.position.repository.GradeRepository;
import com.app.businessBridge.global.RsData.RsData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GradeService {
    private final GradeRepository gradeRepository;

    // 직급 생성
    public RsData<Grade> create(Integer gradeCode, String gradeName) {
        Grade rank = Grade.builder()
                .gradeCode(gradeCode)
                .gradeName(gradeName)
                .build();
        this.gradeRepository.save(rank);

        return RsData.of("S-02", "새로운 리소스가 성공적으로 생성되었습니다.", rank);
    }

    // 직급 모두 불러오기
    public RsData<List<Grade>> read() {
        List<Grade> rankList = this.gradeRepository.findAll();
        return RsData.of("S-05", "요청한 리소스를 찾았습니다.", rankList);
    }

    // 직급 업데이트
    public RsData<Grade> update(Grade grade, Integer gradeCode, String gradeName) {
        grade.setGradeCode(gradeCode);
        grade.setGradeName(gradeName);
        this.gradeRepository.save(grade);

        return RsData.of("S-03", "리소스가 성공적으로 업데이트되었습니다.", grade);
    }

    // 직급 삭제
    public RsData delete(Long id) {
        Optional<Grade> or = findById(id);
        if (or.isEmpty()) {
            return RsData.of("F-04", "요청한 리소스를 찾을 수 없습니다.");
        }

        this.gradeRepository.delete(or.get());
        return RsData.of("S-04", "리소스가 성공적으로 삭제되었습니다.");
    }

    // 직급 단건 찾기 Optional
    public Optional<Grade> findById(Long id) {
        return this.gradeRepository.findById(id);
    }
}
