package com.wanted.preonboarding.article.controller;

import com.wanted.preonboarding.ControllerTestSupport;
import com.wanted.preonboarding.article.controller.request.ArticleCreateControllerRequest;
import com.wanted.preonboarding.article.controller.request.ArticleUpdateControllerRequest;
import com.wanted.preonboarding.article.domain.Article;
import com.wanted.preonboarding.article.service.request.ArticleCreateServiceRequest;
import com.wanted.preonboarding.article.service.request.ArticleDeleteServiceRequest;
import com.wanted.preonboarding.article.service.request.ArticleUpdateServiceRequest;
import com.wanted.preonboarding.content.domain.Content;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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

    @DisplayName("인증된 유저들만 게시글을 삭제할 수 있다.")
    @Test
    public void can_delete_article_by_only_authorized_member() throws Exception {
        //given
        given(tokenService.verify(any(), any()))
                .willReturn("1");

        Article article = createArticle(1L);
        given(articleService.delete(any(ArticleDeleteServiceRequest.class)))
                .willReturn(article);

        //when then
        mockMvc.perform(delete("/api/articles/" + article.getId())
                        .header("authorization", "token")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.articleId").value(article.getId()));
    }

    @DisplayName("누구나 세부 게시글에 접근할 수 있다.")
    @Test
    public void can_access_detail_article_by_anyone() throws Exception {
        //given
        Content content = createContent(2L, "test content");
        given(articleService.getById(any()))
                .willReturn(content);

        //when then
        mockMvc.perform(get("/api/articles/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.contentId").value(content.getId()))
                .andExpect(jsonPath("$.data.content").value(content.getContent()));
    }

    private Article createArticle(Long id) {
        return Article.builder()
                .id(id)
                .build();
    }

    private Content createContent(Long id, String content) {
        return Content.builder()
                .id(id)
                .content(content)
                .build();
    }
}
