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
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ArticleService {
    private final ArticleRepository articleRepository;
    private final ContentRepository contentRepository;

    private final DateService dateService;

    @Transactional
    public Article save(ArticleCreateServiceRequest request) {
        return articleRepository.save(request.toDomain(dateService));
    }

    @Transactional(readOnly = true)
    public Content getById(String articleId) {
        return contentRepository.findByArticleId(articleId).orElseThrow(() -> new IllegalArgumentException("존재하지 않은 게시글입니다."));
    }

    @Transactional
    public Article update(ArticleUpdateServiceRequest request) {
        if(request.isAllBlank()) {
            throw new IllegalArgumentException("제목 또는 본문을 입력해주세요.");
        }

        Article article = getMyArticleById(request.getArticleId(), request.getMemberId());
        if(!request.isUpdated(article)) {
            return article;
        }

        article.updateTitle(request.getTitle());
        article.updateContent(request.getContent());
        article.updateModifiedTime(dateService);

        return articleRepository.save(article);
    }

    @Transactional
    public Article delete(ArticleDeleteServiceRequest request) {
        Article article = getMyArticleById(request.getArticleId(), request.getMemberId());
        articleRepository.delete(article.getId());

        return article;
    }

    //FIXME: 예외
    private Article getMyArticleById(String articleId, String memberId) {
        Article article = articleRepository.findById(articleId).orElseThrow(() -> new IllegalArgumentException("존재하지 않은 게시글입니다."));
        if(!article.isWrittenByMe(memberId)) {
            throw new IllegalArgumentException("수정 권한이 존재하지 않습니다.");
        }

        return article;
    }
}
