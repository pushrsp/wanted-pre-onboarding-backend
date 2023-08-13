package com.wanted.preonboarding.article.controller;

import com.wanted.preonboarding.article.controller.request.ArticleCreateControllerRequest;
import com.wanted.preonboarding.article.controller.request.ArticlePaginationControllerParams;
import com.wanted.preonboarding.article.controller.request.ArticleUpdateControllerRequest;
import com.wanted.preonboarding.article.controller.response.ArticlePaginationControllerResponse;
import com.wanted.preonboarding.article.controller.response.ArticleReadOneControllerResponse;
import com.wanted.preonboarding.article.controller.response.ArticleWriteControllerResponse;
import com.wanted.preonboarding.article.service.ArticleService;
import com.wanted.preonboarding.article.service.request.ArticleDeleteServiceRequest;
import com.wanted.preonboarding.common.argumentresolver.Auth;
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
    public ApiResponse<ArticleWriteControllerResponse> create(@Valid @RequestBody ArticleCreateControllerRequest request, @Auth String memberId) {
        return ApiResponse.ok(ArticleWriteControllerResponse.from(articleService.save(request.toServiceRequest(memberId))));
    }

    @PatchMapping("/api/articles/{articleId}")
    public ApiResponse<ArticleWriteControllerResponse> update(@PathVariable @NotBlank(message = "접근할 수 없는 페이지입니다.") String articleId,
                                                              @RequestBody ArticleUpdateControllerRequest request,
                                                              @Auth String memberId) {
        return ApiResponse.ok(ArticleWriteControllerResponse.from(articleService.update(request.toServiceRequest(memberId, articleId))));
    }

    @DeleteMapping("/api/articles/{articleId}")
    public ApiResponse<ArticleWriteControllerResponse> delete(@PathVariable @NotBlank(message = "접근할 수 없는 페이지입니다.") String articleId, @Auth String memberId) {
        return ApiResponse.ok(ArticleWriteControllerResponse.from(articleService.delete(ArticleDeleteServiceRequest.from(memberId, articleId))));
    }

    @GetMapping("/api/articles/{articleId}")
    public ApiResponse<ArticleReadOneControllerResponse> getByArticleId(@PathVariable @NotBlank(message = "접근할 수 없는 페이지입니다.") String articleId) {
        return ApiResponse.ok(ArticleReadOneControllerResponse.from(articleService.getById(articleId)));
    }

    @GetMapping("/api/articles")
    public ApiResponse<ArticlePaginationControllerResponse> findAll(ArticlePaginationControllerParams params) {
        return ApiResponse.ok(ArticlePaginationControllerResponse.from(params.getSize() + 1, articleService.findAll(params.getPage(), params.getSize() + 1)));
    }
}
