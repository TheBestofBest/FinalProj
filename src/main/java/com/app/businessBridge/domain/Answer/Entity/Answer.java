package com.app.businessBridge.domain.Answer.Entity;

import com.app.businessBridge.domain.Article.Entity.Article;
import com.app.businessBridge.global.Jpa.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@ToString(callSuper = true)
public class Answer extends BaseEntity {

    private String content;

    @ManyToOne
    private Article article;
}
