package com.app.businessBridge.domain.Answer.Service;

import com.app.businessBridge.domain.Answer.Entity.Answer;
import com.app.businessBridge.domain.Answer.Repository.AnswerRepository;
import com.app.businessBridge.domain.Article.Entity.Article;
import com.app.businessBridge.global.RsData.RsCode;
import com.app.businessBridge.global.RsData.RsData;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AnswerService {
    private final AnswerRepository answerRepository;

    public Optional<Answer> getAnswer(Long id){
        return this.answerRepository.findById(id);
    }

    public RsData<Answer> create(String content) {
        Answer answer = Answer.builder()
                .content(content)
                .build();

        this.answerRepository.save(answer);

        return RsData.of(RsCode.S_02,
                "게시물이 생성 되었습니다.",
                answer
        );
    }
}
