package com.wanted.preonboarding.article.controller.response;

import com.wanted.preonboarding.article.domain.Article;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ArticleCreateControllerResponse {
    private Long articleId;

    @Builder
    private ArticleCreateControllerResponse(Long articleId) {
        this.articleId = articleId;
    }

    public static ArticleCreateControllerResponse from(Article article) {
        return ArticleCreateControllerResponse.builder()
                .articleId(article.getId())
                .build();
    }
}
