package com.wanted.preonboarding.article.controller.request;

import com.wanted.preonboarding.article.service.request.ArticleCreateServiceRequest;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
public class ArticleCreateControllerRequest {
    @NotBlank(message = "제목을 입력해주세요.")
    private String title;

    @NotBlank(message = "본문을 입력해주세요.")
    private String content;

    @Builder
    private ArticleCreateControllerRequest(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public ArticleCreateServiceRequest toServiceRequest(String memberId) {
        return ArticleCreateServiceRequest.builder()
                .title(this.title)
                .content(this.content)
                .memberId(memberId)
                .build();
    }
}
