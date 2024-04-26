package com.app.businessBridge.domain.rebate.repository;

import com.app.businessBridge.domain.rebate.entity.Rebate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RebateRepository extends JpaRepository<Rebate, Long> {
}
