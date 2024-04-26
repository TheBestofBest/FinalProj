package com.app.businessBridge.domain.workingstate.repository;

import com.app.businessBridge.domain.workingstate.entity.WorkingState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkingStateRepository extends JpaRepository<WorkingState, Long> {
}
