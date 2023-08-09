package com.wanted.preonboarding.content.infra;

import com.wanted.preonboarding.content.domain.Content;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ContentRepositoryImpl implements ContentRepository {
    private final ContentJpaRepository contentJpaRepository;

    @Override
    public Optional<Content> findByArticleId(Long articleId) {
        return contentJpaRepository.findByArticleId(articleId).map(ContentEntity::toDomain);
    }

    @Override
    public void deleteAllInBatch() {
        contentJpaRepository.deleteAllInBatch();
    }
}
