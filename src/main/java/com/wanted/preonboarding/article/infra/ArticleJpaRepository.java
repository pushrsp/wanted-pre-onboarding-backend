package com.wanted.preonboarding.article.infra;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ArticleJpaRepository extends JpaRepository<ArticleEntity, Long> {
    @Query(value = "select a from ARTICLE a join USER u on u.user_id = a.user_id where a.article_id > :id limit :limit", nativeQuery = true)
    List<ArticleEntity> findAllByLastId(@Param("id") Long id, @Param("limit") int limit);
}
