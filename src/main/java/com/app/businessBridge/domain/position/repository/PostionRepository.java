package com.app.businessBridge.domain.position.repository;

import com.app.businessBridge.domain.position.entity.Position;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostionRepository extends JpaRepository<Position, Long> {
}
