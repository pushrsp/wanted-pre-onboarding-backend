package com.wanted.preonboarding.article.infra;

import com.wanted.preonboarding.article.domain.Article;

import java.util.List;
import java.util.Optional;

public interface ArticleRepository {
    List<Article> findAll(Integer page, Integer size);
    Article save(Article article);
    void delete(String id);
    Optional<Article> findById(String id);
    void deleteAllInBatch();
}
