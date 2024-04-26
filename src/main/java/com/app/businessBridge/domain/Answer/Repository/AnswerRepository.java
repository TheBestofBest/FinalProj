package com.app.businessBridge.domain.Answer.Repository;

import com.app.businessBridge.domain.Answer.Entity.Answer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnswerRepository extends JpaRepository<Answer,Long> {
}
