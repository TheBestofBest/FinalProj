package com.app.businessBridge.domain.grade.repository;

import com.app.businessBridge.domain.grade.entity.Grade;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GradeRepository extends JpaRepository<Grade, Long> {
    List<Grade> findAll();
}
