package com.app.businessBridge.domain.Club.Entity;

import com.app.businessBridge.domain.Article.Entity.Article;
import com.app.businessBridge.global.Jpa.BaseEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
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
public class Club extends BaseEntity {
    private String clubName;// 동아리 이름

    @OneToMany(mappedBy = "club", cascade = CascadeType.REMOVE)
    private List<Article> articleList;
}
