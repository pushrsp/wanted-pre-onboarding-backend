package com.wanted.preonboarding.member.infra;

import com.wanted.preonboarding.member.domain.Member;

import java.util.Optional;

public interface MemberRepository {
    Optional<Member> findById(String id);
    Optional<Member> findByEmail(String email);
    Member save(Member user);
    void deleteAllInBatch();
}
