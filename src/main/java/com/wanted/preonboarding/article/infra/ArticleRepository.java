package com.wanted.preonboarding.article.infra;

import com.wanted.preonboarding.article.domain.Article;

import java.util.List;

public interface ArticleRepository {
    List<Article> findAllByLastId(Long lastId, int limit);
    Article save(Article article);
    Article update(Article article);
    void delete(Long id);
}
