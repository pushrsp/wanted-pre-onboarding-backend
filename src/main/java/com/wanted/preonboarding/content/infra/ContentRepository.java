package com.wanted.preonboarding.content.infra;

import com.wanted.preonboarding.content.domain.Content;

import java.util.Optional;

public interface ContentRepository {
    Optional<Content> findByArticleId(Long articleId);
    void deleteAllInBatch();
}
