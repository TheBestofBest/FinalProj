package com.app.businessBridge.domain.department.service;

import com.app.businessBridge.domain.department.entity.Department;
import com.app.businessBridge.domain.department.repository.DepartmentRepository;
import com.app.businessBridge.global.RsData.RsCode;
import com.app.businessBridge.global.RsData.RsData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DepartmentService {
    private final DepartmentRepository departmentRepository;

    // 부서 생성
    public RsData create(Integer code, String name) {
        // 중복 검사
        if (this.departmentRepository.findByCode(code).isPresent()) {
            return RsData.of(RsCode.F_06, "이미 존재하는 부서코드 입니다.");
        } else if (this.departmentRepository.findByName(name).isPresent()) {
            return RsData.of(RsCode.F_06, "이미 존재하는 부서명 입니다.");
        }

        Department department = Department.builder()
                .code(code)
                .name(name)
                .build();
        this.departmentRepository.save(department);

        return RsData.of(RsCode.S_02, "부서가 생성되었습니다.");
    }

    // 부서 모두 불러오기
    public RsData<List<Department>> findAll() {
        List<Department> departmentList = this.departmentRepository.findAll();

        return RsData.of(RsCode.S_05, "부서목록을 찾았습니다.", departmentList);
    }

    // 부서 업데이트
    public RsData<Department> update(Long id, Integer code, String name) {
        // 부서 찾기
        RsData<Department> rsData = findById(id);

        if (rsData.getData() == null) {
            return rsData;
        }

        // 중복 검사
        if (this.departmentRepository.findByCode(code).isPresent()) {
            return RsData.of(RsCode.F_06, "이미 존재하는 부서코드 입니다.");
        } else if (this.departmentRepository.findByName(name).isPresent()) {
            return RsData.of(RsCode.F_06, "이미 존재하는 부서명 입니다.");
        }

        Department department = rsData.getData().toBuilder()
                .code(code)
                .name(name)
                .build();

        this.departmentRepository.save(department);

        return RsData.of(RsCode.S_03, "부서가 업데이트되었습니다.", department);
    }

    // 부서 삭제
    public RsData delete(Long id) {
        // 부서 찾기
        RsData<Department> rsData = findById(id);
        // 없으면 그냥 리턴
        if (rsData.getData() == null) {
            return rsData;
        }

        this.departmentRepository.delete(rsData.getData());
        return RsData.of(RsCode.S_04, "부서가 삭제되었습니다.");
    }

    // 부서 단건 찾기 Optional
    public RsData<Department> findById(Long id) {
        Optional<Department> od = this.departmentRepository.findById(id);
        if (od.isEmpty()) {
            return RsData.of(RsCode.F_04, "부서를 찾을 수 없습니다.", null);
        }

        return RsData.of(RsCode.S_05, "부서를 찾았습니다.", od.get());
    }
}
