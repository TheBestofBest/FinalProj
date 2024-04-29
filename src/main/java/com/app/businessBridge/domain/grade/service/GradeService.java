package com.app.businessBridge.domain.grade.service;

import com.app.businessBridge.domain.grade.DTO.GradeDTO;
import com.app.businessBridge.domain.grade.entity.Grade;
import com.app.businessBridge.domain.grade.repository.GradeRepository;
import com.app.businessBridge.global.RsData.RsCode;
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
    public RsData<GradeDTO> create(Integer gradeCode, String gradeName) {
        Grade grade = Grade.builder()
                .gradeCode(gradeCode)
                .gradeName(gradeName)
                .build();
        this.gradeRepository.save(grade);

        return RsData.of(RsCode.S_02, "새로운 리소스가 성공적으로 생성되었습니다.", new GradeDTO(grade));
    }

    // 직급 모두 불러오기
    public RsData<List<GradeDTO>> readAll() {
        List<GradeDTO> gradeDTOList = this.gradeRepository
                .findAll()
                .stream()
                .map(grade -> new GradeDTO(grade))
                .toList();
        return RsData.of(RsCode.S_05, "요청한 리소스목록을 찾았습니다.", gradeDTOList);
    }

    // 직급 업데이트
    public RsData<GradeDTO> update(Long id, Integer gradeCode, String gradeName) {
        RsData<Grade> rsData = findById(id);

        if (rsData.getData() == null) {
            return RsData.of(rsData.getRsCode(), rsData.getMsg(), null);
        }

        Grade grade = rsData.getData().toBuilder()
                .gradeCode(gradeCode)
                .gradeName(gradeName)
                .build();

        this.gradeRepository.save(grade);

        return RsData.of(RsCode.S_03, "리소스가 성공적으로 업데이트되었습니다.", new GradeDTO(grade));
    }

    // 직급 삭제
    public RsData<GradeDTO> delete(Long id) {
        RsData<Grade> rsData = findById(id);

        // result code 문제 해결 필요
        if (rsData.getData() == null) {
            return RsData.of(rsData.getRsCode(), rsData.getMsg(), null);
        }

        this.gradeRepository.delete(rsData.getData());
        return RsData.of(RsCode.S_04, "리소스가 성공적으로 삭제되었습니다.", null);
    }

    // 직급 단건 찾기 Optional
    public RsData<Grade> findById(Long id) {
        Optional<Grade> og = this.gradeRepository.findById(id);
        if (og.isEmpty()) {
            return RsData.of(RsCode.F_04, "요청한 리소스를 찾을 수 없습니다.", null);
        }
        return RsData.of(RsCode.S_05, "요청한 리소스를 찾았습니다.", og.get());
    }
}
