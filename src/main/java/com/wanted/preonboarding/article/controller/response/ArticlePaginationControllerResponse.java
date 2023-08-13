package com.wanted.preonboarding.article.controller.response;

import com.wanted.preonboarding.article.domain.Article;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class ArticlePaginationControllerResponse {
    private boolean hasNext;
    private List<ArticleResponse> articles;

    @Builder
    private ArticlePaginationControllerResponse(boolean hasNext, List<ArticleResponse> articles) {
        this.hasNext = hasNext;
        this.articles = articles;
    }

    public static ArticlePaginationControllerResponse from(Integer size, List<Article> articles) {
        boolean hasNext = articles.size() > size;
        List<ArticleResponse> articleResponses = articles.stream()
                .map(ArticleResponse::from)
                .collect(Collectors.toList());

        if(hasNext) {
            articleResponses.remove(articleResponses.size() - 1);
        }

        return ArticlePaginationControllerResponse.builder()
                .hasNext(hasNext)
                .articles(articleResponses)
                .build();
    }

    @Getter
    static class ArticleResponse {
        private String articleId;
        private String title;
        private String writerId;
        private String writerEmail;
        private LocalDateTime createdTime;
        private LocalDateTime modifiedTime;

        @Builder
        private ArticleResponse(String articleId, String title, String writerId, String writerEmail, LocalDateTime createdTime, LocalDateTime modifiedTime) {
            this.articleId = articleId;
            this.title = title;
            this.writerId = writerId;
            this.writerEmail = writerEmail;
            this.createdTime = createdTime;
            this.modifiedTime = modifiedTime;
        }

        public static ArticleResponse from(Article article) {
            return ArticleResponse.builder()
                    .articleId(article.getId())
                    .title(article.getTitle())
                    .writerId(article.getMember().getId())
                    .writerEmail(article.getMember().getEmail())
                    .createdTime(article.getCreatedTime())
                    .modifiedTime(article.getModifiedTime())
                    .build();
        }
    }
}
