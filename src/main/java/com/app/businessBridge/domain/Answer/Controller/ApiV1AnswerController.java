package com.app.businessBridge.domain.Answer.Controller;

import com.app.businessBridge.domain.Answer.DTO.AnswerDto;
import com.app.businessBridge.domain.Answer.Entity.Answer;
import com.app.businessBridge.domain.Answer.Service.AnswerService;
import com.app.businessBridge.domain.Article.Controller.ApiV1ArticleController;
import com.app.businessBridge.domain.Article.DTO.ArticleDto;
import com.app.businessBridge.domain.Article.Entity.Article;
import com.app.businessBridge.domain.Article.Service.ArticleService;
import com.app.businessBridge.global.RsData.RsCode;
import com.app.businessBridge.global.RsData.RsData;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/answers")
public class ApiV1AnswerController {
    private final ArticleService articleService;
    private final AnswerService answerService;

    @GetMapping("")
    public RsData<AnswersResponses> getAnswers() {
        List<AnswerDto> answerDtoList = this.answerService
                .getList()
                .stream()
                .map(answer -> new AnswerDto(answer))
                .toList();

        return RsData.of(RsCode.S_01, "성공", new AnswersResponses(answerDtoList));
    }
    @GetMapping("/{articleId}/articles")
    public RsData<AnswersResponses> getAnswerAndArticle(@PathVariable(value = "articleId") Long articleId) {
        List<Answer> answers = this.answerService.findAllByArticleId(articleId);
        List<AnswerDto> answerDTOS = new ArrayList<>();
        for (Answer answer : answers) {
            answerDTOS.add(new AnswerDto(answer));
        }

        return RsData.of(RsCode.S_01, "성공", new AnswersResponses(answerDTOS));
    }

    @GetMapping("/{id}")
    public RsData<AnswersResponse> getAnswer(@PathVariable("id") Long id) {
        return answerService.getAnswer(id).map(answer -> RsData.of(RsCode.S_01,
                "성공",
                new AnswersResponse(new AnswerDto(answer))
        )).orElseGet(() -> RsData.of(RsCode.F_01,
                "%d 번  댓글은 존재하지 않습니다.".formatted(id),
                null
        ));
    }


    @PostMapping("")
    public RsData<AnswerResponse> write(@Valid @RequestBody AnswerRequest answerRequest) {

        RsData<Answer> writeRs = this.answerService.create(answerRequest.getContent());

//        if (writeRs.isFail()) return (RsData) writeRs;

        return RsData.of(
                writeRs.getRsCode(),
                writeRs.getMsg(),
                new AnswerResponse(new AnswerDto(writeRs.getData()))
        );
    }
    @PatchMapping("/{id}")
    public RsData<ModifyResponse> modify(@Valid @RequestBody ModifyRequest modifyRequest, @PathVariable("id") Long id) {
        Optional<Answer> optionalAnswer = this.answerService.findById(id);


        if (optionalAnswer.isEmpty()) return RsData.of(RsCode.F_01,
                "%d번 댓글은 존재하지 않습니다.".formatted(id),
                null
        );

        RsData<Answer> modifyRs = this.answerService.modify(optionalAnswer.get(),modifyRequest.getContent());

        return RsData.of(
                modifyRs.getRsCode(),
                modifyRs.getMsg(),
                new ApiV1AnswerController.ModifyResponse(modifyRs.getData())
        );
    }

    @DeleteMapping("/{id}")
    public RsData<RemoveResponse> remove(@PathVariable("id") Long id) {
        Optional<Answer> optionalAnswer = this.answerService.findById(id);

        if (optionalAnswer.isEmpty()) return RsData.of(RsCode.F_01,
                "%d번 댓글은 존재하지 않습니다.".formatted(id),
                null
        );

        RsData<Answer> deleteRs = answerService.deleteById(id);

        return RsData.of(
                deleteRs.getRsCode(),
                deleteRs.getMsg(),
                new ApiV1AnswerController.RemoveResponse(optionalAnswer.get())
        );
    }
    @AllArgsConstructor
    @Getter
    public static class AnswersResponses {
        private final List<AnswerDto> answers;
    }

    @AllArgsConstructor
    @Getter
    public static class AnswersResponse {
        private final AnswerDto answer;
    }


    @Data
    public static class AnswerRequest{
        @NotBlank
        private String content;
    }
    @AllArgsConstructor
    @Getter
    public static class AnswerResponse{
        private final AnswerDto answerDto;
    }
    @Data
    public static class ModifyRequest {
        @NotBlank
        private String content;
    }

    @AllArgsConstructor
    @Getter
    public static class ModifyResponse {
        private final Answer answer;
    }

    @AllArgsConstructor
    @Getter
    public static class RemoveResponse {
        private final Answer answer;
    }

}
