package com.wanted.preonboarding.article.controller.response;

import com.wanted.preonboarding.article.domain.Article;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ArticleWriteControllerResponse {
    private String articleId;

    @Builder
    private ArticleWriteControllerResponse(String articleId) {
        this.articleId = articleId;
    }

    public static ArticleWriteControllerResponse from(Article article) {
        return ArticleWriteControllerResponse.builder()
                .articleId(article.getId())
                .build();
    }
}
