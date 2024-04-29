package com.app.businessBridge.domain.department.service;

import com.app.businessBridge.domain.department.DTO.DepartmentDTO;
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
    public RsData<DepartmentDTO> create(Integer departmentCode, String departmentName) {
        Department department = Department.builder()
                .departmentCode(departmentCode)
                .departmentName(departmentName)
                .build();
        this.departmentRepository.save(department);

        return RsData.of(RsCode.S_02, "새로운 리소스가 성공적으로 생성되었습니다.", new DepartmentDTO(department));
    }

    // 부서 모두 불러오기
    public RsData<List<DepartmentDTO>> readAll() {
        List<DepartmentDTO> departmentDTOList = this.departmentRepository
                .findAll()
                .stream()
                .map(department -> new DepartmentDTO(department))
                .toList();
        return RsData.of(RsCode.S_05, "요청한 리소스목록을 찾았습니다.", departmentDTOList);
    }

    // 부서 업데이트
    public RsData<DepartmentDTO> update(Long id, Integer departmentCode, String departmentName) {
        RsData<Department> rsData = findById(id);

        if (rsData.getData() == null) {
            return RsData.of(rsData.getRsCode(), rsData.getMsg(), null);
        }

        Department department = rsData.getData().toBuilder()
                .departmentCode(departmentCode)
                .departmentName(departmentName)
                .build();

        this.departmentRepository.save(department);

        return RsData.of(RsCode.S_03, "리소스가 성공적으로 업데이트되었습니다.", new DepartmentDTO(department));
    }

    // 부서 삭제
    public RsData<DepartmentDTO> delete(Long id) {
        RsData<Department> rsData = findById(id);

        if (rsData.getData() == null) {
            return RsData.of(rsData.getRsCode(), rsData.getMsg(), null);
        }

        this.departmentRepository.delete(rsData.getData());
        return RsData.of(RsCode.S_04, "리소스가 성공적으로 삭제되었습니다.", null);
    }

    // 부서 단건 찾기 Optional
    public RsData<Department> findById(Long id) {
        Optional<Department> od = this.departmentRepository.findById(id);
        if (od.isEmpty()) {
            return RsData.of(RsCode.F_04, "요청한 리소스를 찾을 수 없습니다.", null);
        }
        return RsData.of(RsCode.S_05, "요청한 리소스를 찾았습니다.", od.get());
    }
}
