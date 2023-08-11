package com.wanted.preonboarding.article.service.request;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ArticleDeleteServiceRequest {
    String memberId;
    String articleId;

    @Builder
    private ArticleDeleteServiceRequest(String memberId, String articleId) {
        this.memberId = memberId;
        this.articleId = articleId;
    }

    public static ArticleDeleteServiceRequest from(String memberId, String articleId) {
        return ArticleDeleteServiceRequest.builder()
                .memberId(memberId)
                .articleId(articleId)
                .build();
    }
}
