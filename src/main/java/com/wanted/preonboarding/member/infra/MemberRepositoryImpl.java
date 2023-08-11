package com.wanted.preonboarding.member.infra;

import com.wanted.preonboarding.common.exception.WrongIdFormatException;
import com.wanted.preonboarding.common.utils.IdUtils;
import com.wanted.preonboarding.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Repository
@RequiredArgsConstructor
public class MemberRepositoryImpl implements MemberRepository {
    private final MemberJpaRepository memberJpaRepository;

    @Override
    public Optional<Member> findById(String id) {
        if(!IdUtils.isOnlyNumber(id)) {
            throw new WrongIdFormatException("id 형식이 잘못되었습니다.");
        }

        return memberJpaRepository.findById(Long.parseLong(id)).map(MemberEntity::toDomain);
    }

    @Override
    public Optional<Member> findByEmail(String email) {
        return memberJpaRepository.findByEmail(email).map(MemberEntity::toDomain);
    }

    @Override
    public Member save(Member user) {
        return memberJpaRepository.save(MemberEntity.from(user)).toDomain();
    }

    @Override
    public void deleteAllInBatch() {
        memberJpaRepository.deleteAllInBatch();
    }
}
