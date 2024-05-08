package com.app.businessBridge.domain.department.repository;

import com.app.businessBridge.domain.department.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DepartmentRepository extends JpaRepository<Department,Long> {
    List<Department> findAll();
    Optional<Department> findByCode(Integer code);
    Optional<Department> findByName(String name);
}
