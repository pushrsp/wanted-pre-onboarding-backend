package com.wanted.preonboarding.article.service;

import com.wanted.preonboarding.article.domain.Article;
import com.wanted.preonboarding.article.infra.ArticleRepository;
import com.wanted.preonboarding.article.service.request.ArticleCreateServiceRequest;
import com.wanted.preonboarding.article.service.request.ArticleDeleteServiceRequest;
import com.wanted.preonboarding.article.service.request.ArticleUpdateServiceRequest;
import com.wanted.preonboarding.common.service.date.DateService;
import com.wanted.preonboarding.content.domain.Content;
import com.wanted.preonboarding.content.infra.ContentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ArticleService {
    private final ArticleRepository articleRepository;
    private final ContentRepository contentRepository;

    private final DateService dateService;

    public Article save(ArticleCreateServiceRequest request) {
        return articleRepository.save(request.toDomain(dateService));
    }

    public Content getById(Long articleId) {
        return contentRepository.findByArticleId(articleId).orElseThrow(() -> new IllegalArgumentException("존재하지 않은 게시글입니다."));
    }

    public void update(ArticleUpdateServiceRequest request) {
        Article article = getMyArticleById(request.getArticleId(), request.getMemberId());

        article.updateTitle(request.getTitle());
        article.updateContent(request.getContent());

        articleRepository.save(article);
    }

    public void delete(ArticleDeleteServiceRequest request) {
        Article article = getMyArticleById(request.getArticleId(), request.getMemberId());
        articleRepository.delete(article.getId());
    }

    //FIXME: 예외
    private Article getMyArticleById(Long articleId, Long memberId) {
        Article article = articleRepository.findById(articleId).orElseThrow(() -> new IllegalArgumentException("존재하지 않은 게시글입니다."));
        if(!article.isWrittenByMe(memberId)) {
            throw new IllegalArgumentException("수정 권한이 존재하지 않습니다.");
        }

        return article;
    }
}
