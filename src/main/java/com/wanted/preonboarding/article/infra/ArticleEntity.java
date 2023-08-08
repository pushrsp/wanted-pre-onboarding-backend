package com.wanted.preonboarding.article.infra;

import com.wanted.preonboarding.article.domain.Article;
import com.wanted.preonboarding.content.infra.ContentEntity;
import com.wanted.preonboarding.member.infra.MemberEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "ARTICLE")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ArticleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "article_id")
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "created_time", nullable = false)
    private LocalDateTime createdTime;

    @Column(name = "created_date", nullable = false)
    private LocalDate createdDate;

    @Column(name = "modified_time", nullable = false)
    private LocalDateTime modifiedTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private MemberEntity member;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "article")
    private ContentEntity content;

    @Builder
    private ArticleEntity(Long id, String title, LocalDateTime createdTime, LocalDate createdDate, LocalDateTime modifiedTime, MemberEntity member, ContentEntity content) {
        this.id = id;
        this.title = title;
        this.createdTime = createdTime;
        this.createdDate = createdDate;
        this.modifiedTime = modifiedTime;
        this.member = member;
        this.content = content;
    }

    public static ArticleEntity from(Article article) {
        ArticleEntity articleEntity = ArticleEntity.builder()
                .title(article.getTitle())
                .createdTime(article.getCreatedTime())
                .createdDate(article.getCreatedDate())
                .modifiedTime(article.getModifiedTime())
                .build();

        articleEntity.addMember(MemberEntity.from(article.getMember()));
        articleEntity.addContent(ContentEntity.from(article.getContent()));

        return articleEntity;
    }

    private void addMember(MemberEntity member) {
        this.member = member;
        this.member.addArticle(this);
    }

    private void addContent(ContentEntity content) {
        this.content = content;
        this.content.addArticle(this);
    }

    public Article toDomain() {
        return Article.builder()
                .id(this.id)
                .title(this.title)
                .createdTime(this.createdTime)
                .createdDate(this.createdDate)
                .modifiedTime(this.modifiedTime)
                .build();
    }
}
