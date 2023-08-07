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
    @DisplayName("ArticleEntity는 ArticleDomain으로 부 생성할 수 있다.")
    @Test
    public void can_create_article_entity_from_article_domain() throws Exception {
        //given
        LocalDateTime now = LocalDateTime.of(2023, 8, 7, 14, 9);
        Member userDomain = createUserDomain(1L, "abc@naver.com", "password", now);
        Content contentDomain = createContentDomain(1L, "content");
        Article articleDomain = createArticleDomain(null, "title", now, now.toLocalDate(), now, userDomain, contentDomain);

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
        Member userDomain = createUserDomain(1L, "abc@naver.com", "password", now);
        Content contentDomain = createContentDomain(1L, "content");

        ArticleEntity articleEntity = ArticleEntity.from(createArticleDomain(null, "title", now, now.toLocalDate(), now, userDomain, contentDomain));

        //when
        Article articleDomain = articleEntity.toDomain();

        //then
        assertThat(articleDomain.getId()).isEqualTo(articleEntity.getId());
        assertThat(articleDomain.getTitle()).isEqualTo(articleEntity.getTitle());
        assertThat(articleDomain.getCreatedTime()).isEqualTo(articleEntity.getCreatedTime());
        assertThat(articleDomain.getCreatedDate()).isEqualTo(articleEntity.getCreatedDate());
        assertThat(articleDomain.getModifiedTime()).isEqualTo(articleEntity.getModifiedTime());
    }

    private Content createContentDomain(Long id, String content) {
        return Content.builder()
                .id(id)
                .content(content)
                .build();
    }

    private Member createUserDomain(Long id, String email, String password, LocalDateTime createdTime) {
        return Member.builder()
                .id(id)
                .email(email)
                .password(password)
                .createdTime(createdTime)
                .build();
    }

    private Article createArticleDomain(Long id, String title, LocalDateTime createdTime, LocalDate createdDate, LocalDateTime modifiedTime, Member user, Content content) {
        return Article.builder()
                .id(id)
                .title(title)
                .createdTime(createdTime)
                .createdDate(createdDate)
                .modifiedTime(modifiedTime)
                .user(user)
                .content(content)
                .build();
    }
}
