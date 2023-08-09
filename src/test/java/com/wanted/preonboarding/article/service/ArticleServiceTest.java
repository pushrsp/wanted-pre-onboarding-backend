package com.wanted.preonboarding.article.service;

import com.wanted.preonboarding.IntegrationTestSupport;
import com.wanted.preonboarding.article.domain.Article;
import com.wanted.preonboarding.article.infra.ArticleRepository;
import com.wanted.preonboarding.article.service.request.ArticleCreateServiceRequest;
import com.wanted.preonboarding.article.service.request.ArticleDeleteServiceRequest;
import com.wanted.preonboarding.article.service.request.ArticleUpdateServiceRequest;
import com.wanted.preonboarding.common.service.date.DateService;
import com.wanted.preonboarding.content.domain.Content;
import com.wanted.preonboarding.content.infra.ContentRepository;
import com.wanted.preonboarding.member.domain.Member;
import com.wanted.preonboarding.member.infra.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class ArticleServiceTest extends IntegrationTestSupport {
    @Autowired
    private ArticleService articleService;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private ContentRepository contentRepository;

    @AfterEach
    void tearDown() {
        contentRepository.deleteAllInBatch();
        articleRepository.deleteAllInBatch();
        memberRepository.deleteAllInBatch();
    }

    @DisplayName("ArticleCreateServiceRequest를 통해 article을 생성할 수 있다.")
    @Test
    public void can_save_article_by_ArticleCreateServiceRequest() throws Exception {
        //given
        LocalDateTime now = LocalDateTime.of(2023, 8, 9, 12, 50);
        Long memberId = saveMember(createMemberDomain("abc@naver.com", "password", now));
        String title = "title";
        String content = "content";

        ArticleCreateServiceRequest request = ArticleCreateServiceRequest.builder()
                .memberId(memberId)
                .title(title)
                .content(content)
                .build();

        //when
        Article savedArticle = articleService.save(request);

        //then
        assertThat(savedArticle.getId()).isNotNull();
        assertThat(savedArticle.getTitle()).isEqualTo(title);
        assertThat(savedArticle.getContent().getId()).isNotNull();
        assertThat(savedArticle.getContent().getContent()).isEqualTo(content);
    }

    @DisplayName("ArticleUpdateServiceRequest를 통해 article을 수정할 수 있다.")
    @Test
    public void can_update_article_by_ArticleUpdateServiceRequest() throws Exception {
        //given
        LocalDateTime now = LocalDateTime.of(2023, 8, 9, 12, 50);
        Article savedArticle = saveArticle(now, "title", "content");

        ArticleUpdateServiceRequest request = ArticleUpdateServiceRequest.builder()
                .title("updatedTitle")
                .content("updatedContent")
                .memberId(savedArticle.getMember().getId())
                .articleId(savedArticle.getId())
                .build();

        //when
        Article updatedArticle = articleService.update(request);

        //then
        assertThat(updatedArticle.getId()).isEqualTo(savedArticle.getId());
        assertThat(updatedArticle.getTitle()).isEqualTo(request.getTitle());
        assertThat(updatedArticle.getContent().getContent()).isEqualTo(request.getContent());
        assertThat(updatedArticle.getModifiedTime()).isAfter(savedArticle.getModifiedTime());
    }

    @DisplayName("ArticleId를 통해 content를 찾을 수 있다.")
    @Test
    public void can_find_content_by_articleId() throws Exception {
        //given
        LocalDateTime now = LocalDateTime.of(2023, 8, 9, 12, 50);
        Article savedArticle = saveArticle(now, "title", "content");

        //when
        Content content = articleService.getById(savedArticle.getId());

        //then
        assertThat(content.getContent()).isEqualTo(savedArticle.getContent().getContent());
        assertThat(content.getId()).isEqualTo(savedArticle.getContent().getId());
    }

    @DisplayName("articleId와 memberId를 통해 article을 삭제할 수 있다.")
    @Test
    public void can_delete_article_by_articleId_and_memberId() throws Exception {
        //given
        LocalDateTime now = LocalDateTime.of(2023, 8, 9, 12, 50);
        Article savedArticle = saveArticle(now, "title", "content");

        ArticleDeleteServiceRequest request = ArticleDeleteServiceRequest.builder()
                .articleId(savedArticle.getId())
                .memberId(savedArticle.getMember().getId())
                .build();

        //when
        articleService.delete(request);

        //then
        Optional<Article> optionalArticle = articleRepository.findById(savedArticle.getId());
        assertThat(optionalArticle.isEmpty()).isTrue();

        Optional<Content> optionalContent = contentRepository.findByArticleId(savedArticle.getId());
        assertThat(optionalContent.isEmpty()).isTrue();
    }

    @DisplayName("ArticleId를 통해 조회 시 데이터가 없으면 예외를 던진다.")
    @Test
    public void can_detect_article_is_existed_by_articleId() throws Exception {
        //given

        //when
        IllegalArgumentException illegalArgumentException = catchThrowableOfType(() -> articleService.getById(0L), IllegalArgumentException.class);

        //then
        assertThat(illegalArgumentException).isNotNull();
        assertThat(illegalArgumentException.getMessage()).isEqualTo("존재하지 않은 게시글입니다.");
    }

    private Article saveArticle(LocalDateTime now, String title, String content) {
        Long memberId = saveMember(createMemberDomain("abc@naver.com", "password", now));
        ArticleCreateServiceRequest request = ArticleCreateServiceRequest.builder()
                .memberId(memberId)
                .title(title)
                .content(content)
                .build();

        return articleRepository.save(request.toDomain(getDateService(now)));
    }

    private DateService getDateService(LocalDateTime now) {
        return new DateService() {
            @Override
            public LocalDate getDate() {
                return now.toLocalDate();
            }

            @Override
            public LocalDateTime getDateTime() {
                return now;
            }
        };
    }

    private Long saveMember(Member member) {
        Member savedMember = memberRepository.save(member);
        return savedMember.getId();
    }

    private Member createMemberDomain(String email, String password, LocalDateTime createdTime) {
        return Member.builder()
                .email(email)
                .password(password)
                .createdTime(createdTime)
                .build();
    }
}
