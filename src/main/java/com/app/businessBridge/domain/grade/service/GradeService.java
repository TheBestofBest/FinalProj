package com.app.businessBridge.domain.grade.service;

import com.app.businessBridge.domain.grade.entity.Grade;
import com.app.businessBridge.domain.grade.repository.GradeRepository;
import com.app.businessBridge.global.RsData.RsCode;
import com.app.businessBridge.global.RsData.RsData;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GradeService {
    private final GradeRepository gradeRepository;

    // 직급 생성
    public RsData create(Integer code, String name) {
        // 중복 검사
        if (this.gradeRepository.findByCode(code).isPresent()) {
            return RsData.of(RsCode.F_06, "이미 존재하는 직급코드 입니다.");
        } else if (this.gradeRepository.findByName(name).isPresent()) {
            return RsData.of(RsCode.F_06, "이미 존재하는 직급명 입니다.");
        }

        Grade grade = Grade.builder()
                .code(code)
                .name(name)
                .build();
        this.gradeRepository.save(grade);

        return RsData.of(RsCode.S_02, "직급이 생성되었습니다.");
    }

    // 직급 모두 불러오기
    public RsData<List<Grade>> findAll() {
        List<Grade> gradeDTOList = this.gradeRepository.findAll();

        return RsData.of(RsCode.S_05, "직급목록을 찾았습니다.", gradeDTOList);
    }

    // 직급 업데이트
    public RsData<Grade> update(Long id, Integer code, String name) {
        RsData<Grade> rsData = findById(id);

        if (rsData.getData() == null) {
            return rsData;
        }
        Optional<Grade> og = this.gradeRepository.findByCode(code);
        // 중복 검사
        if (og.isPresent()) {
            if (og.get().getId() != id) {
                return RsData.of(RsCode.F_06, "이미 존재하는 직급코드 입니다.");
            }
        } else if (og.isPresent()) {
            if(og.get().getId()!=id){
                return RsData.of(RsCode.F_06, "이미 존재하는 직급명 입니다.");
            }
        }

        Grade grade = rsData.getData().toBuilder()
                .code(code)
                .name(name)
                .build();

        this.gradeRepository.save(grade);

        return RsData.of(RsCode.S_03, "직급이 업데이트되었습니다.", grade);
    }

    // 직급 삭제
    public RsData delete(Long id) {
        RsData<Grade> rsData = findById(id);

        // result code 문제 해결 필요
        if (rsData.getData() == null) {
            return RsData.of(rsData.getRsCode(), rsData.getMsg());
        }
        try {
        this.gradeRepository.delete(rsData.getData());
        } catch (DataIntegrityViolationException e) {
            return RsData.of(RsCode.F_05, "직급 내 회원이 존재하여 삭제할 수 없습니다.");
        }
        return RsData.of(RsCode.S_04, "직급이 삭제되었습니다.");
    }

    // 직급 단건 찾기 Optional
    public RsData<Grade> findById(Long id) {
        Optional<Grade> og = this.gradeRepository.findById(id);
        if (og.isEmpty()) {
            return RsData.of(RsCode.F_04, "직급을 찾을 수 없습니다.", null);
        }
        return RsData.of(RsCode.S_05, "직급을 찾았습니다.", og.get());
    }
}
