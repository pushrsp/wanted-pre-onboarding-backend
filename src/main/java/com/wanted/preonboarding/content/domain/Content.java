package com.wanted.preonboarding.content.domain;

import com.wanted.preonboarding.article.domain.Article;
import lombok.Builder;
import lombok.Getter;

@Getter
public class Content {
    private Long id;
    private String content;
    private Article article;

    @Builder
    private Content(Long id, String content) {
        this.id = id;
        this.content = content;
    }

    public void addArticle(Article article) {
        this.article = article;
    }
}
