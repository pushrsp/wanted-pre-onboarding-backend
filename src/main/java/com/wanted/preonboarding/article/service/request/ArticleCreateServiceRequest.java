package com.wanted.preonboarding.article.service.request;

import com.wanted.preonboarding.article.domain.Article;
import com.wanted.preonboarding.common.service.date.DateService;
import com.wanted.preonboarding.content.domain.Content;
import com.wanted.preonboarding.member.domain.Member;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ArticleCreateServiceRequest {
    private Long memberId;
    private String title;
    private String content;

    @Builder
    private ArticleCreateServiceRequest(Long memberId, String title, String content) {
        this.memberId = memberId;
        this.title = title;
        this.content = content;
    }

    public Article toDomain(DateService dateService) {
        Member member = Member.builder()
                .id(memberId)
                .build();

        Content content = Content.builder()
                .content(this.content)
                .build();

        LocalDateTime now = dateService.getDateTime();
        Article article = Article.builder()
                .member(member)
                .title(this.title)
                .createdTime(now)
                .createdDate(now.toLocalDate())
                .modifiedTime(now)
                .build();

        article.addContent(content);

        return article;
    }
}
