package com.wanted.preonboarding.article.controller;

import com.wanted.preonboarding.article.controller.request.ArticleCreateControllerRequest;
import com.wanted.preonboarding.article.controller.request.ArticleUpdateControllerRequest;
import com.wanted.preonboarding.article.controller.response.ArticleCreateControllerResponse;
import com.wanted.preonboarding.article.service.ArticleService;
import com.wanted.preonboarding.common.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

@RestController
@RequiredArgsConstructor
public class ArticleController {
    private final ArticleService articleService;

    @PostMapping("/api/articles")
    public ApiResponse<ArticleCreateControllerResponse> create(@Valid @RequestBody ArticleCreateControllerRequest request) {
        return ApiResponse.ok(ArticleCreateControllerResponse.from(articleService.save(request.toServiceRequest(1L))));
    }

    @PatchMapping("/api/articles/{articleId}")
    public void update(@PathVariable @NotBlank(message = "수정 권한이 존재하지 않습니다.") Long articleId,
                       @RequestBody ArticleUpdateControllerRequest request) {
        articleService.update(request.toServiceRequest(1L, articleId));
    }
}
