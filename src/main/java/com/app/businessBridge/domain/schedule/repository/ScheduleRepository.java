package com.app.businessBridge.domain.schedule.repository;

import com.app.businessBridge.domain.schedule.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
}
