package com.wanted.preonboarding.article.infra;

import com.wanted.preonboarding.IntegrationTestSupport;
import com.wanted.preonboarding.article.domain.Article;
import com.wanted.preonboarding.content.domain.Content;
import com.wanted.preonboarding.content.infra.ContentRepository;
import com.wanted.preonboarding.member.domain.Member;
import com.wanted.preonboarding.member.infra.MemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.*;

class ArticleRepositoryTest extends IntegrationTestSupport {
    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ContentRepository contentRepository;

    @AfterEach
    void tearDown() {
        contentRepository.deleteAllInBatch();
        articleRepository.deleteAllInBatch();
        memberRepository.deleteAllInBatch();
    }

    @DisplayName("ArticleDomain을 통해 Article을 생성할 수 있다.")
    @Test
    public void can_save_article_by_article_domain() throws Exception {
        //given
        LocalDateTime now = LocalDateTime.of(2023, 8, 7, 17, 47);
        Member savedMember = memberRepository.save(createMemberDomain("abc@naver.com", "test", now));

        Article articleDomain = createArticleDomain(now, now.toLocalDate(), now, savedMember);
        articleDomain.addContent(createContentDomain());

        //when
        Article savedArticle = articleRepository.save(articleDomain);

        //then
        assertThat(savedArticle.getId()).isNotNull();
    }

    @DisplayName("ArticleId를 통해 Article을 삭제할 수 있다.")
    @Test
    public void can_delete_article_by_id() throws Exception {
        //given
        LocalDateTime now = LocalDateTime.of(2023, 8, 7, 17, 47);
        Member savedMember = memberRepository.save(createMemberDomain("abc@naver.com", "test", now));

        Article articleDomain = createArticleDomain(now, now.toLocalDate(), now, savedMember);
        articleDomain.addContent(createContentDomain());

        Article savedArticle = articleRepository.save(articleDomain);

        //when
        articleRepository.delete(savedArticle.getId());

        //then
        Optional<Article> optionalArticle = articleRepository.findById(savedArticle.getId());
        assertThat(optionalArticle.isEmpty()).isTrue();
    }

    @DisplayName("limit과 offset을 통해 원하는 개수만큼 article을 얻어올 수 있다.")
    @ParameterizedTest
    @MethodSource("provideLimitAndOffset")
    public void can_get_articles_by_limit_and_offset(int size, int limit, int offset, int expected) throws Exception {
        //given
        LocalDateTime now = LocalDateTime.of(2023, 8, 14, 17, 47);
        Member savedMember = memberRepository.save(createMemberDomain("abc@naver.com", "test", now));

        for (int i = 0; i < size; i++) {
            Article articleDomain = createArticleDomain(now, now.toLocalDate(), now, savedMember);
            articleDomain.addContent(createContentDomain());

            articleRepository.save(articleDomain);
        }

        //when
        List<Article> articles = articleRepository.findAll(offset, limit);

        //then
        assertThat(articles).hasSize(expected);
    }

    private static Stream<Arguments> provideLimitAndOffset() {
        return Stream.of(
                Arguments.of(10, 10, 0, 10),
                Arguments.of(5, 10, 0, 5),
                Arguments.of(10, 10, 10, 0),
                Arguments.of(15, 5, 10, 5)
        );
    }

    private Member createMemberDomain(String email, String password, LocalDateTime createdTime) {
        return Member.builder()
                .email(email)
                .password(password)
                .createdTime(createdTime)
                .build();
    }

    private Content createContentDomain() {
        return Content.builder()
                .content("content")
                .build();
    }

    private Article createArticleDomain(LocalDateTime createdTime, LocalDate createdDate, LocalDateTime modifiedTime, Member member) {
        return Article.builder()
                .title("title")
                .member(member)
                .createdTime(createdTime)
                .createdDate(createdDate)
                .modifiedTime(modifiedTime)
                .build();
    }
}
