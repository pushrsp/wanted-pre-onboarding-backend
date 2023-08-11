package com.wanted.preonboarding.article.controller.response;

import com.wanted.preonboarding.content.domain.Content;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ArticleReadOneControllerResponse {
    private String contentId;
    private String content;

    @Builder
    private ArticleReadOneControllerResponse(String contentId, String content) {
        this.contentId = contentId;
        this.content = content;
    }

    public static ArticleReadOneControllerResponse from(Content content) {
        return ArticleReadOneControllerResponse.builder()
                .contentId(content.getId())
                .content(content.getContent())
                .build();
    }
}
