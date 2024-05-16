package com.app.businessBridge.domain.division.service;

import com.app.businessBridge.domain.division.entity.Division;
import com.app.businessBridge.domain.division.repository.DivisionRepository;
import com.app.businessBridge.global.RsData.RsCode;
import com.app.businessBridge.global.RsData.RsData;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DivisionService {
    private final DivisionRepository divisionRepository;

    // 소속 생성
    public RsData create(Integer code, String name) {
        // 중복 검사
        if (this.divisionRepository.findByCode(code).isPresent()) {
            return RsData.of(RsCode.F_06, "이미 존재하는 소속 코드 입니다.");
        } else if (this.divisionRepository.findByName(name).isPresent()) {
            return RsData.of(RsCode.F_06, "이미 존재하는 소속명 입니다.");
        }

        Division division = Division.builder()
                .code(code)
                .name(name)
                .build();
        this.divisionRepository.save(division);

        return RsData.of(RsCode.S_02, "소속이 생성되었습니다.");
    }

    // 소속 모두 불러오기
    public RsData<List<Division>> findAll() {
        List<Division> divisionList = this.divisionRepository.findAll();

        return RsData.of(RsCode.S_05, "소속목록을 찾았습니다.", divisionList);
    }

    // 소속 업데이트
    public RsData<Division> update(Long id, Integer code, String name) {
        // 소속 찾기
        RsData<Division> rsData = findById(id);

        if (rsData.getData() == null) {
            return rsData;
        }

        // 중복 검사
        Optional<Division> odv = this.divisionRepository.findByCode(code);

        if (odv.isPresent()) {
            if (odv.get().getId() != id) {
                return RsData.of(RsCode.F_06, "이미 존재하는 소속코드 입니다.");
            }
        } else if (odv.isPresent()) {
            if (odv.get().getId() != id) {
                return RsData.of(RsCode.F_06, "이미 존재하는 소속명 입니다.");
            }
        }

        Division division = rsData.getData().toBuilder()
                .code(code)
                .name(name)
                .build();

        this.divisionRepository.save(division);

        return RsData.of(RsCode.S_03, "소속이 업데이트되었습니다.", division);
    }

    // 소속 삭제
    public RsData delete(Long id) {
        // 소속 찾기
        RsData<Division> rsData = findById(id);
        // 없으면 그냥 리턴
        if (rsData.getData() == null) {
            return rsData;
        }
        try {
            this.divisionRepository.delete(rsData.getData());
        } catch (DataIntegrityViolationException e) {
            return RsData.of(RsCode.F_05, "소속 내 회원이 존재하여 삭제할 수 없습니다.");
        }
        return RsData.of(RsCode.S_04, "소속이 삭제되었습니다.");
    }

    // 소속 단건 찾기 Optional
    public RsData<Division> findById(Long id) {
        Optional<Division> od = this.divisionRepository.findById(id);
        if (od.isEmpty()) {
            return RsData.of(RsCode.F_04, "소속을 찾을 수 없습니다.", null);
        }

        return RsData.of(RsCode.S_05, "소속을 찾았습니다.", od.get());
    }
}
