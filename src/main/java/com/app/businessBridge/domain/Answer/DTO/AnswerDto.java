package com.app.businessBridge.domain.Answer.DTO;

import com.app.businessBridge.domain.Answer.Entity.Answer;
import lombok.Getter;

@Getter
public class AnswerDto {
    private Long id;
    private String content;

    public AnswerDto (Answer answer) {
        this.id = answer.getId();
        this.content = answer.getContent();
    }
}
