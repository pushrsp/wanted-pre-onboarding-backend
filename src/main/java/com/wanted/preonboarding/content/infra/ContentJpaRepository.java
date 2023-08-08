package com.wanted.preonboarding.content.infra;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ContentJpaRepository extends JpaRepository<ContentEntity, Long> {
    @Query("select c from ContentEntity c join fetch c.article where c.article.id = :articleId")
    Optional<ContentEntity> findByArticleId(@Param("articleId") Long articleId);
}
