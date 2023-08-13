package com.wanted.preonboarding.member.infra;

import com.wanted.preonboarding.member.domain.Member;

import java.util.List;
import java.util.Optional;

public interface MemberRepository {
    Optional<Member> findById(String id);
    List<Member>findAllByIdIn(List<Long> ids);
    Optional<Member> findByEmail(String email);
    Member save(Member user);
    void deleteAllInBatch();
}
