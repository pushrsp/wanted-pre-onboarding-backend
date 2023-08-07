package com.wanted.preonboarding.content.infra;

import com.wanted.preonboarding.article.infra.ArticleEntity;
import com.wanted.preonboarding.content.domain.Content;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@Table(name = "CONTENT")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ContentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "content_id")
    private Long id;

    @Column(name = "content", nullable = false)
    private String content;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_id")
    private ArticleEntity article;

    @Builder
    private ContentEntity(Long id, String content, ArticleEntity article) {
        this.id = id;
        this.content = content;
        this.article = article;
    }

    public static ContentEntity from(Content content) {
        return ContentEntity.builder()
                .content(content.getContent())
                .build();
    }

    public Content toDomain() {
        return Content.builder()
                .id(this.id)
                .content(this.content)
                .build();
    }
}