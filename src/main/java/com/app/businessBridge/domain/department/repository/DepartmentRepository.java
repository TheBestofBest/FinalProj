package com.app.businessBridge.domain.department.repository;

import com.app.businessBridge.domain.department.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department,Long> {
}
