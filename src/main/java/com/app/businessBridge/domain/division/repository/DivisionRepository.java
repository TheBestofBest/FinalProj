package com.app.businessBridge.domain.division.repository;

import com.app.businessBridge.domain.division.entity.Division;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DivisionRepository extends JpaRepository<Division,Long> {
    List<Division> findAll();
    Optional<Division> findByCode(Integer code);
    Optional<Division> findByName(String name);
}
