package com.wanted.preonboarding.article.controller;

import com.wanted.preonboarding.article.controller.request.ArticleCreateControllerRequest;
import com.wanted.preonboarding.article.controller.response.ArticleCreateControllerResponse;
import com.wanted.preonboarding.article.service.ArticleService;
import com.wanted.preonboarding.common.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class ArticleController {
    private final ArticleService articleService;

    @PostMapping("/api/articles")
    public ApiResponse<ArticleCreateControllerResponse> create(@Valid @RequestBody ArticleCreateControllerRequest request) {
        return ApiResponse.ok(ArticleCreateControllerResponse.from(articleService.save(request.toServiceRequest(1L))));
    }
}
