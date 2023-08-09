package com.wanted.preonboarding.article.service.request;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ArticleUpdateServiceRequest {
    Long memberId;
    Long articleId;
    String title;
    String content;

    @Builder
    private ArticleUpdateServiceRequest(Long memberId, Long articleId, String title, String content) {
        this.memberId = memberId;
        this.articleId = articleId;
        this.title = title;
        this.content = content;
    }
}
