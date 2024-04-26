package com.app.businessBridge.domain.confirm.repository;

import com.app.businessBridge.domain.confirm.entity.Confirm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConfirmRepository extends JpaRepository<Confirm, Long> {
}
