package com.wanted.preonboarding.article.controller;

import com.wanted.preonboarding.ControllerTestSupport;
import com.wanted.preonboarding.article.controller.request.ArticleCreateControllerRequest;
import com.wanted.preonboarding.article.controller.request.ArticleUpdateControllerRequest;
import com.wanted.preonboarding.article.domain.Article;
import com.wanted.preonboarding.article.service.request.ArticleCreateServiceRequest;
import com.wanted.preonboarding.article.service.request.ArticleUpdateServiceRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class ArticleControllerTest extends ControllerTestSupport {
    @DisplayName("인증된 유저들만 게시글을 생성할 수 있다.")
    @Test
    public void can_create_article_by_only_authorized_member() throws Exception {
        //given
        ArticleCreateControllerRequest request = ArticleCreateControllerRequest.builder()
                .title("title")
                .content("content")
                .build();

        given(tokenService.verify(any(), any()))
                        .willReturn("1");

        Article article = createArticle(1L);
        given(articleService.save(any(ArticleCreateServiceRequest.class)))
                .willReturn(article);

        //when then
        mockMvc.perform(post("/api/articles")
                        .header("authorization", "token")
                        .content(objectMapper.writeValueAsBytes(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.articleId").value(article.getId()));
    }

    @DisplayName("인증된 유저들만 게시글을 수정할 수 있다.")
    @Test
    public void can_update_article_by_only_authorized_member() throws Exception {
        //given
        ArticleUpdateControllerRequest request = ArticleUpdateControllerRequest.builder()
                .content("content")
                .title("title")
                .build();

        given(tokenService.verify(any(), any()))
                .willReturn("1");

        Article article = createArticle(1L);
        given(articleService.update(any(ArticleUpdateServiceRequest.class)))
                .willReturn(article);

        //when then
        mockMvc.perform(patch("/api/articles/"+article.getId())
                        .content(objectMapper.writeValueAsBytes(request))
                        .header("authorization", "token")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.articleId").value(article.getId()));
    }

    private static Article createArticle(Long id) {
        return Article.builder()
                .id(id)
                .build();
    }
}
