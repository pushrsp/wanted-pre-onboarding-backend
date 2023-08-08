package com.wanted.preonboarding.article.infra;

import com.wanted.preonboarding.RepositoryIntegrationTestSupport;
import com.wanted.preonboarding.article.domain.Article;
import com.wanted.preonboarding.content.domain.Content;
import com.wanted.preonboarding.member.domain.Member;
import com.wanted.preonboarding.member.infra.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;

@Transactional
class ArticleRepositoryTest extends RepositoryIntegrationTestSupport {
    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private MemberRepository memberRepository;

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
