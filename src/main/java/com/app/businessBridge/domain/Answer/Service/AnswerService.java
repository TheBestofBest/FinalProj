package com.app.businessBridge.domain.Answer.Service;

import com.app.businessBridge.domain.Answer.Entity.Answer;
import com.app.businessBridge.domain.Answer.Repository.AnswerRepository;
import com.app.businessBridge.domain.Article.Entity.Article;
import com.app.businessBridge.domain.Article.Repository.ArticleRepository;
import com.app.businessBridge.domain.Article.Service.ArticleService;
import com.app.businessBridge.global.RsData.RsCode;
import com.app.businessBridge.global.RsData.RsData;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AnswerService {
    private final ArticleRepository articleRepository;
    private final AnswerRepository answerRepository;

    public List<Answer> getList() {
        return this.answerRepository.findAll();
    }

    public Optional<Answer> getAnswer(Long id){
        return this.answerRepository.findById(id);
    }

    @Transactional
    public RsData<Answer> create(String content, Article article) {
        Answer answer = Answer.builder()
                .content(content)
                .article(article)
                .build();

        this.answerRepository.save(answer);

        return RsData.of(RsCode.S_02,
                "게시물이 생성 되었습니다.",
                answer
        );
    }
    public Optional<Answer> findById(Long id) {
        return answerRepository.findById(id);
    }
    public RsData<Answer> modify(Answer answer,String content) {
        answer.setContent(content);
        answerRepository.save(answer);

        return RsData.of(RsCode.S_03,
                "%d번 게시물이 수정 되었습니다.".formatted(answer.getId()),
                answer
        );
    }

    public RsData<Answer> deleteById(Long id) {
        answerRepository.deleteById(id);

        return RsData.of(RsCode.S_04,
                "%d번 게시물이 삭제 되었습니다.".formatted(id),
                null
        );
    }
    public List<Answer> findAllByArticleId(Long articleId) {
        return this.answerRepository.findAllByArticleId(articleId);
    }
}