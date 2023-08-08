package com.wanted.preonboarding.content.infra;

import com.wanted.preonboarding.IntegrationTestSupport;
import com.wanted.preonboarding.article.domain.Article;
import com.wanted.preonboarding.article.infra.ArticleRepository;
import com.wanted.preonboarding.content.domain.Content;
import com.wanted.preonboarding.member.domain.Member;
import com.wanted.preonboarding.member.infra.MemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

class ContentRepositoryTest extends IntegrationTestSupport {
    @Autowired
    private ContentRepository contentRepository;

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private MemberRepository memberRepository;

    @AfterEach
    void tearDown() {
        contentRepository.deleteAllInBatch();
        articleRepository.deleteAllInBatch();
        memberRepository.deleteAllInBatch();
    }

    @DisplayName("ArticleId를 통해 Content를 찾을 수 있다.")
    @Test
    public void can_find_content_by_article_id() throws Exception {
        //given
        LocalDateTime now = LocalDateTime.of(2023, 8, 7, 17, 47);
        Member savedMember = memberRepository.save(createMemberDomain("abc@naver.com", "test", now));

        Article articleDomain = createArticleDomain(now, now.toLocalDate(), now, savedMember);
        articleDomain.addContent(createContentDomain());

        Article savedArticle = articleRepository.save(articleDomain);

        //when
        Optional<Content> optionalContent = contentRepository.findByArticleId(savedArticle.getId());

        //then
        assertThat(optionalContent.isPresent()).isTrue();
        assertThat(optionalContent.get().getContent()).isEqualTo(articleDomain.getContent().getContent());
        assertThat(optionalContent.get().getArticle().getTitle()).isEqualTo(articleDomain.getTitle());
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
