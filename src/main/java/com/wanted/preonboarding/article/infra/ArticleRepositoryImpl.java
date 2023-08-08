package com.wanted.preonboarding.article.infra;

import com.wanted.preonboarding.article.domain.Article;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class ArticleRepositoryImpl implements ArticleRepository {
    private final ArticleJpaRepository articleJpaRepository;

    @Override
    public List<Article> findAllByLastId(Long lastId, int limit) {
        return articleJpaRepository.findAllByLastId(lastId, limit).stream()
                .map(ArticleEntity::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Article save(Article article) {
        return articleJpaRepository.save(ArticleEntity.from(article)).toDomain();
    }

    @Override
    public void delete(Long id) {
        articleJpaRepository.deleteById(id);
    }

    @Override
    public Optional<Article> findById(Long id) {
        return articleJpaRepository.findById(id).map(ArticleEntity::toDomain);
    }

    @Override
    public void deleteAllInBatch() {
        articleJpaRepository.deleteAllInBatch();
    }
}
