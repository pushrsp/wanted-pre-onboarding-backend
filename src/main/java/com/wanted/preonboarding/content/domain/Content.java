package com.wanted.preonboarding.content.domain;

import com.wanted.preonboarding.article.domain.Article;
import lombok.Builder;
import lombok.Getter;

@Getter
public class Content {
    private String id;
    private String content;
    private Article article;

    @Builder
    private Content(String id, String content, Article article) {
        this.id = id;
        this.content = content;
        this.article = article;
    }

    public void addArticle(Article article) {
        this.article = article;
    }

    //Article 도메인을 통해 접근해야됨!
    public void updateContent(String content) {
        this.content = content;
    }
}
