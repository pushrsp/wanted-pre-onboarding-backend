package com.wanted.preonboarding.article.domain;

import com.wanted.preonboarding.content.domain.Content;
import com.wanted.preonboarding.user.domain.User;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
public class Article {
    private Long id;
    private String title;
    private User user;
    private Content content;
    private LocalDateTime createdTime;
    private LocalDate createdDate;
    private LocalDateTime modifiedTime;

    @Builder
    private Article(Long id, String title, User user, Content content , LocalDateTime createdTime, LocalDate createdDate, LocalDateTime modifiedTime) {
        this.id = id;
        this.title = title;
        this.user = user;
        this.content = content;
        this.createdTime = createdTime;
        this.createdDate = createdDate;
        this.modifiedTime = modifiedTime;
    }
}