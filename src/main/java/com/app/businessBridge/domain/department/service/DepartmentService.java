package com.app.businessBridge.domain.department.service;

import com.app.businessBridge.domain.department.entity.Department;
import com.app.businessBridge.domain.department.repository.DepartmentRepository;
import com.app.businessBridge.global.RsData.RsData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DepartmentService {
    private final DepartmentRepository departmentRepository;

    public RsData<Department> create(Integer departmentCode, String departmentName) {
        Department department = Department.builder()
                .departmentCode(departmentCode)
                .departmentName(departmentName)
                .build();
        this.departmentRepository.save(department);

        return RsData.of("S-1", "success", department);
    }
}
