package com.wanted.preonboarding.content.infra;

import com.wanted.preonboarding.common.exception.WrongIdFormatException;
import com.wanted.preonboarding.common.utils.IdUtils;
import com.wanted.preonboarding.content.domain.Content;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ContentRepositoryImpl implements ContentRepository {
    private final ContentJpaRepository contentJpaRepository;

    @Override
    public Optional<Content> findByArticleId(String articleId) {
        if(!IdUtils.isOnlyNumber(articleId)) {
            throw new WrongIdFormatException("id 형식이 잘못되었습니다.");
        }

        return contentJpaRepository.findByArticleId(Long.parseLong(articleId)).map(ContentEntity::toDomain);
    }

    @Override
    public void deleteAllInBatch() {
        contentJpaRepository.deleteAllInBatch();
    }
}
