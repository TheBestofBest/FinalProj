package com.app.businessBridge.domain.Article.Controller;

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

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/articles")
public class ApiV1ArticleController {

    private final ArticleService articleService;

    @GetMapping("")
    public RsData<ArticlesResponse> getArticles() {
        List<ArticleDto> articleDtoList = this.articleService
                .getList()
                .stream()
                .map(article -> new ArticleDto(article))
                .toList();

        return RsData.of(RsCode.S_01, "성공", new ArticlesResponse(articleDtoList));
    }

    @GetMapping("/{id}")
    public RsData<ArticleResponse> getArticle(@PathVariable("id") Long id) {
        return articleService.getArticle(id).map(article -> RsData.of(RsCode.S_01,
                "성공",
                new ArticleResponse(new ArticleDto(article))
        )).orElseGet(() -> RsData.of(RsCode.F_01,
                "%d 번 게시물은 존재하지 않습니다.".formatted(id),
                null
        ));
    }
    @PostMapping("")
    public RsData<WriteResponse> write(@Valid @RequestBody WriteRequest writeRequest) {


        RsData<Article> writeRs = this.articleService.create(writeRequest.getSubject(), writeRequest.getContent());

//        if (writeRs.isFail()) return (RsData) writeRs;

        return RsData.of(
                writeRs.getRsCode(),
                writeRs.getMsg(),
                new WriteResponse(new ArticleDto(writeRs.getData()))
        );
    }
    @PatchMapping("/{id}")
    public RsData modify(@Valid @RequestBody ModifyRequest modifyRequest, @PathVariable("id") Long id) {
        Optional<Article> optionalArticle = this.articleService.findById(id);

        if (optionalArticle.isEmpty()) return RsData.of(RsCode.F_01,
                "%d번 게시물은 존재하지 않습니다.".formatted(id),
                null
        );

        RsData<Article> modifyRs = this.articleService.modify(optionalArticle.get(), modifyRequest.getSubject(), modifyRequest.getContent());

        return RsData.of(
                modifyRs.getRsCode(),
                modifyRs.getMsg(),
                new ModifyResponse(modifyRs.getData())
        );
    }

    @DeleteMapping("/{id}")
    public RsData<RemoveResponse> remove(@PathVariable("id") Long id) {
        Optional<Article> optionalArticle = this.articleService.findById(id);

        if (optionalArticle.isEmpty()) return RsData.of(RsCode.F_01,
                "%d번 게시물은 존재하지 않습니다.".formatted(id),
                null
        );

        RsData<Article> deleteRs = articleService.deleteById(id);

        return RsData.of(
                deleteRs.getRsCode(),
                deleteRs.getMsg(),
                new RemoveResponse(optionalArticle.get())
        );
    }

    @AllArgsConstructor
    @Getter
    public static class ArticlesResponse {
        private final List<ArticleDto> articles;
    }

    @AllArgsConstructor
    @Getter
    public static class ArticleResponse {
        private final ArticleDto article;
    }

    @Data
    public static class WriteRequest {
        @NotBlank
        private String subject;
        @NotBlank
        private String content;
    }

    @AllArgsConstructor
    @Getter
    public static class WriteResponse {
        private final ArticleDto articledto;
    }
    @Data
    public static class ModifyRequest {
        @NotBlank
        private String subject;
        @NotBlank
        private String content;
    }

    @AllArgsConstructor
    @Getter
    public static class ModifyResponse {
        private final Article article;
    }

    @AllArgsConstructor
    @Getter
    public static class RemoveResponse {
        private final Article article;
    }
}
