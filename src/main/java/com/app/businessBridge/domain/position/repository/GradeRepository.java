package com.app.businessBridge.domain.position.repository;

import com.app.businessBridge.domain.position.entity.Grade;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GradeRepository extends JpaRepository<Grade, Long> {
    List<Grade> findAll();
}
