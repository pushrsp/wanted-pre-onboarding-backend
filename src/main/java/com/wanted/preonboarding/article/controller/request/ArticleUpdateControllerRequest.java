package com.wanted.preonboarding.article.controller.request;

import com.wanted.preonboarding.article.service.request.ArticleUpdateServiceRequest;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ArticleUpdateControllerRequest {
    private String title;
    private String content;

    @Builder
    private ArticleUpdateControllerRequest(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public ArticleUpdateServiceRequest toServiceRequest(Long memberId, Long articleId) {
        return ArticleUpdateServiceRequest.builder()
                .title(this.title)
                .content(this.content)
                .memberId(memberId)
                .articleId(articleId)
                .build();
    }
}
