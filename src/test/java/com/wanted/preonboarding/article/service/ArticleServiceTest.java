package com.wanted.preonboarding.article.service;

import com.wanted.preonboarding.IntegrationTestSupport;
import com.wanted.preonboarding.article.domain.Article;
import com.wanted.preonboarding.article.infra.ArticleRepository;
import com.wanted.preonboarding.article.service.request.ArticleCreateServiceRequest;
import com.wanted.preonboarding.common.service.date.DateService;
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
