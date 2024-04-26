package com.app.businessBridge.domain.Article.Repository;

import com.app.businessBridge.domain.Article.Entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article ,Long> {
}
