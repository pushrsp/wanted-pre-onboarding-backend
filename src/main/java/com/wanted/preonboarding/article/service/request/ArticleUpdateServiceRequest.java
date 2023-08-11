package com.wanted.preonboarding.article.service.request;

import com.wanted.preonboarding.article.domain.Article;
import lombok.Builder;
import lombok.Getter;
import org.springframework.util.StringUtils;

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

    public boolean isAllBlank() {
        return !StringUtils.hasText(this.title) && !StringUtils.hasText(this.content);
    }

    public boolean isUpdated(Article article) {
        boolean updated = false;
        if(this.title != null && !this.title.equals(article.getTitle())) {
            updated = true;
        }

        if(this.content != null && !this.content.equals(article.getContent().getContent())) {
            updated = true;
        }

        return updated;
    }
}
