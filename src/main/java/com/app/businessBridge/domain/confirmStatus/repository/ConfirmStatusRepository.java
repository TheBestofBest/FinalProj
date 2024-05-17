package com.app.businessBridge.domain.confirmStatus.repository;

import com.app.businessBridge.domain.confirmStatus.entity.ConfirmStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ConfirmStatusRepository extends JpaRepository<ConfirmStatus, Long> {
    Optional<ConfirmStatus> findByStatusName(String statusName);
}
