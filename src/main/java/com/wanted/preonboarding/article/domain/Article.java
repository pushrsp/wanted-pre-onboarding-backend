package com.wanted.preonboarding.article.domain;

import com.wanted.preonboarding.content.domain.Content;
import com.wanted.preonboarding.member.domain.Member;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
public class Article {
    private Long id;
    private String title;
    private Member member;
    private Content content;
    private LocalDateTime createdTime;
    private LocalDate createdDate;
    private LocalDateTime modifiedTime;

    @Builder
    private Article(Long id, String title, Member member, LocalDateTime createdTime, LocalDate createdDate, LocalDateTime modifiedTime) {
        this.id = id;
        this.title = title;
        this.member = member;
        this.createdTime = createdTime;
        this.createdDate = createdDate;
        this.modifiedTime = modifiedTime;
    }

    public void addContent(Content content) {
        this.content = content;
        this.content.addArticle(this);
    }
}
