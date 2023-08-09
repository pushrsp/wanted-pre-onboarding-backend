package com.wanted.preonboarding.article.service.request;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ArticleDeleteServiceRequest {
    Long memberId;
    Long articleId;

    @Builder
    private ArticleDeleteServiceRequest(Long memberId, Long articleId) {
        this.memberId = memberId;
        this.articleId = articleId;
    }
}
