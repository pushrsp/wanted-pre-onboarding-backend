package com.wanted.preonboarding.article.infra;

import com.wanted.preonboarding.article.domain.Article;
import com.wanted.preonboarding.common.exception.WrongIdFormatException;
import com.wanted.preonboarding.common.utils.IdUtils;
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
        return articleJpaRepository.save(ArticleEntity.from(article)).toDomainWithMemberAndContent();
    }

    @Override
    public void delete(String id) {
        if(!IdUtils.isOnlyNumber(id)) {
            throw new WrongIdFormatException("id 형식이 잘못되었습니다.");
        }

        articleJpaRepository.deleteById(Long.parseLong(id));
    }

    @Override
    public Optional<Article> findById(String id) {
        if(!IdUtils.isOnlyNumber(id)) {
            throw new WrongIdFormatException("id 형식이 잘못되었습니다.");
        }

        return articleJpaRepository.findById(Long.parseLong(id)).map(ArticleEntity::toDomainWithMemberAndContent);
    }

    @Override
    public void deleteAllInBatch() {
        articleJpaRepository.deleteAllInBatch();
    }
}
