package com.app.businessBridge.domain.Article.DTO;


import com.app.businessBridge.domain.Article.Entity.Article;
import lombok.Getter;

@Getter
public class ArticleDto {
    private Long id;
    private String subject;
    private String content;

    public ArticleDto (Article article){
        this.id = article.getId();
        this.subject = article.getSubject();
        this.content = article.getContent();
    }
}
