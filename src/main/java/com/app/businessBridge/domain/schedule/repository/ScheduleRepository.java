package com.app.businessBridge.domain.schedule.repository;

import com.app.businessBridge.domain.schedule.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    List<Schedule> findByRelationNameAndRelationId(String relationName, Long relationId);

}
