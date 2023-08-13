package com.wanted.preonboarding.article.infra;

import com.wanted.preonboarding.article.domain.Article;

import java.util.List;
import java.util.Optional;

public interface ArticleRepository {
    List<Article> findAllByLastId(Long lastId, int limit);
    Article save(Article article);
    void delete(String id);
    Optional<Article> findById(String id);
    void deleteAllInBatch();
}
