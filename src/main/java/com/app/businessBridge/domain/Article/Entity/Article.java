package com.app.businessBridge.domain.Article.Entity;

import com.app.businessBridge.domain.Answer.Entity.Answer;
import com.app.businessBridge.domain.Club.Entity.Club;
import com.app.businessBridge.global.Jpa.BaseEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@ToString(callSuper = true)
public class Article extends BaseEntity {
    private String subject;
    private String content;

    @OneToMany(mappedBy = "article", cascade = CascadeType.REMOVE)
    private List<Answer> answerList;

    @ManyToOne
    private Club club;
}
