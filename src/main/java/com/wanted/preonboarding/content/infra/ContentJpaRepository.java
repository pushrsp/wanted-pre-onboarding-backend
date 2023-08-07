package com.wanted.preonboarding.content.infra;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ContentJpaRepository extends JpaRepository<ContentEntity, Long> {
    @Query(value = "select c from CONTENT c where c.article_id = :articleId", nativeQuery = true)
    Optional<ContentEntity> findByArticleId(@Param("articleId") Long articleId);
}
