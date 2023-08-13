package com.wanted.preonboarding.article.infra;

import com.wanted.preonboarding.article.domain.Article;
import com.wanted.preonboarding.content.domain.Content;
import com.wanted.preonboarding.member.domain.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;

class ArticleEntityTest {
    @DisplayName("ArticleEntity는 ArticleDomain으로 생성할 수 있다.")
    @Test
    public void can_create_article_entity_from_article_domain() throws Exception {
        //given
        LocalDateTime now = LocalDateTime.of(2023, 8, 7, 14, 9);
        Member userDomain = createUserDomain("1", "abc@naver.com", "password", now);

        Article articleDomain = createArticleDomain(null, "title", now, now.toLocalDate(), now, userDomain);
        articleDomain.addContent(createContentDomain("1", "content"));

        //when
        ArticleEntity articleEntity = ArticleEntity.from(articleDomain);

        //then
        assertThat(articleEntity.getId()).isNull();
        assertThat(articleEntity.getTitle()).isEqualTo(articleDomain.getTitle());
        assertThat(articleEntity.getCreatedTime()).isEqualTo(articleDomain.getCreatedTime());
        assertThat(articleEntity.getCreatedDate()).isEqualTo(articleDomain.getCreatedDate());
        assertThat(articleEntity.getModifiedTime()).isEqualTo(articleDomain.getModifiedTime());
    }

    @DisplayName("ArticleEntity는 ArticleDomain으로 컨버팅 할 수 있다.")
    @Test
    public void article_entity_can_convert_to_article_domain() throws Exception {
        //given
        LocalDateTime now = LocalDateTime.of(2023, 8, 7, 14, 9);
        Member userDomain = createUserDomain("1", "abc@naver.com", "password", now);

        Article articleDomain = createArticleDomain(null, "title", now, now.toLocalDate(), now, userDomain);
        articleDomain.addContent(createContentDomain("1", "content"));
        ArticleEntity articleEntity = ArticleEntity.from(articleDomain);

        //when
        Article result = articleEntity.toDomain();

        //then
        assertThat(String.valueOf(result.getId())).isEqualTo(String.valueOf(articleEntity.getId()));
        assertThat(result.getTitle()).isEqualTo(articleEntity.getTitle());
        assertThat(result.getCreatedTime()).isEqualTo(articleEntity.getCreatedTime());
        assertThat(result.getCreatedDate()).isEqualTo(articleEntity.getCreatedDate());
        assertThat(result.getModifiedTime()).isEqualTo(articleEntity.getModifiedTime());
    }

    private Content createContentDomain(String id, String content) {
        return Content.builder()
                .id(id)
                .content(content)
                .build();
    }

    private Member createUserDomain(String id, String email, String password, LocalDateTime createdTime) {
        return Member.builder()
                .id(id)
                .email(email)
                .password(password)
                .createdTime(createdTime)
                .build();
    }

    private Article createArticleDomain(String id, String title, LocalDateTime createdTime, LocalDate createdDate, LocalDateTime modifiedTime, Member member) {
        return Article.builder()
                .id(id)
                .title(title)
                .createdTime(createdTime)
                .createdDate(createdDate)
                .modifiedTime(modifiedTime)
                .member(member)
                .build();
    }
}
