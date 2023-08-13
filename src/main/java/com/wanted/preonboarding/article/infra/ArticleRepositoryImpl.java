package com.wanted.preonboarding.article.infra;

import com.wanted.preonboarding.article.domain.Article;
import com.wanted.preonboarding.common.exception.WrongIdFormatException;
import com.wanted.preonboarding.common.utils.IdUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ArticleRepositoryImpl implements ArticleRepository {
    private final ArticleJpaRepository articleJpaRepository;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private static final RowMapper<Article> ARTICLE_ROW_MAPPER = (ResultSet rs, int rowNum) -> {
        Article article = Article.builder()
                .id(String.valueOf(rs.getLong("article_id")))
                .title(rs.getString("title"))
                .modifiedTime(rs.getTimestamp("modified_time").toLocalDateTime())
                .createdTime(rs.getTimestamp("created_time").toLocalDateTime())
                .build();

        article.addMemberId(String.valueOf(rs.getLong("member_id")));

        return article;
    };

    @Override
    public List<Article> findAll(Integer page, Integer size) {
        String sql = "SELECT * FROM ARTICLE a " +
                "ORDER BY a.created_date DESC, a.article_id DESC " +
                "LIMIT :limit " +
                "OFFSET :offset";

        return namedParameterJdbcTemplate.query(sql, generateParams(page, size), ARTICLE_ROW_MAPPER);
    }

    private SqlParameterSource generateParams(Integer page, Integer size) {
        return new MapSqlParameterSource()
                .addValue("limit", size)
                .addValue("offset", page);
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
