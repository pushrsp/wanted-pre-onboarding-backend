package com.wanted.preonboarding.article.domain;

import com.wanted.preonboarding.common.service.date.DateService;
import com.wanted.preonboarding.content.domain.Content;
import com.wanted.preonboarding.member.domain.Member;
import lombok.Builder;
import lombok.Getter;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

@Getter
public class Article {
    private String id;
    private String title;
    private Member member;
    private Content content;
    private LocalDateTime createdTime;
    private LocalDate createdDate;
    private LocalDateTime modifiedTime;

    @Builder
    private Article(String id, String title, Member member, LocalDateTime createdTime, LocalDate createdDate, LocalDateTime modifiedTime, Content content) {
        this.id = id;
        this.title = title;
        this.member = member;
        this.content = content;
        this.createdTime = createdTime;
        this.createdDate = createdDate;
        this.modifiedTime = modifiedTime;
    }

    public void addContent(Content content) {
        this.content = content;
        this.content.addArticle(this);
    }

    public boolean isWrittenByMe(String memberId) {
        return Objects.equals(this.member.getId(), memberId);
    }

    public void updateTitle(String title) {
        if(hasText(title)) {
            this.title = title;
        }
    }

    public void updateContent(String content) {
        if(hasText(content)) {
            this.content.updateContent(content);
        }
    }

    public void updateModifiedTime(DateService dateService) {
        this.modifiedTime = dateService.getDateTime();
    }

    private boolean hasText(String text) {
        return StringUtils.hasText(text);
    }
}
