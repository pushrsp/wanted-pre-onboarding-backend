package com.wanted.preonboarding.article.controller;

import com.wanted.preonboarding.article.controller.request.ArticleCreateControllerRequest;
import com.wanted.preonboarding.article.controller.request.ArticleUpdateControllerRequest;
import com.wanted.preonboarding.article.controller.response.ArticleReadOneControllerResponse;
import com.wanted.preonboarding.article.controller.response.ArticleWriteControllerResponse;
import com.wanted.preonboarding.article.service.ArticleService;
import com.wanted.preonboarding.article.service.request.ArticleDeleteServiceRequest;
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
    public ApiResponse<ArticleWriteControllerResponse> create(@Valid @RequestBody ArticleCreateControllerRequest request) {
        return ApiResponse.ok(ArticleWriteControllerResponse.from(articleService.save(request.toServiceRequest(1L))));
    }

    @PatchMapping("/api/articles/{articleId}")
    public ApiResponse<ArticleWriteControllerResponse> update(@PathVariable @NotBlank(message = "수정 권한이 존재하지 않습니다.") Long articleId,
                       @RequestBody ArticleUpdateControllerRequest request) {
        return ApiResponse.ok(ArticleWriteControllerResponse.from(articleService.update(request.toServiceRequest(1L, articleId))));
    }

    @DeleteMapping("/api/articles/{articleId}")
    public ApiResponse<ArticleWriteControllerResponse> delete(@PathVariable @NotBlank(message = "수정 권한이 존재하지 않습니다.") Long articleId) {
        return ApiResponse.ok(ArticleWriteControllerResponse.from(articleService.delete(ArticleDeleteServiceRequest.from(1L, articleId))));
    }

    @GetMapping("/api/articles/{articleId}")
    public ApiResponse<ArticleReadOneControllerResponse> getByArticleId(@PathVariable @NotBlank(message = "수정 권한이 존재하지 않습니다.") Long articleId) {
        return ApiResponse.ok(ArticleReadOneControllerResponse.from(articleService.getById(articleId)));
    }
}
