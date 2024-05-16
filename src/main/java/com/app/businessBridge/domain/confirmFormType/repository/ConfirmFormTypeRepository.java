package com.app.businessBridge.domain.confirmFormType.repository;

import com.app.businessBridge.domain.confirmFormType.entity.ConfirmFormType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ConfirmFormTypeRepository extends JpaRepository<ConfirmFormType, Long> {
    Optional<ConfirmFormType> findByFormName(String formName);
}
