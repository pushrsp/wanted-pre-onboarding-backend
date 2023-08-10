package com.wanted.preonboarding.member.service;

import com.wanted.preonboarding.common.service.date.DateService;
import com.wanted.preonboarding.common.service.password.PasswordService;
import com.wanted.preonboarding.common.service.token.TokenService;
import com.wanted.preonboarding.member.domain.Member;
import com.wanted.preonboarding.member.infra.MemberRepository;
import com.wanted.preonboarding.member.service.request.MemberCreateServiceRequest;
import com.wanted.preonboarding.member.service.request.MemberLoginServiceRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordService passwordService;
    private final DateService dateService;
    private final TokenService tokenService;

    @Value("${token.secret}")
    private String secret;

    @Value("${token.offset}")
    private Long offset;

    @Transactional
    public Long create(MemberCreateServiceRequest request) {
        verifyMember(request);

        Member member = request.toDomain(dateService);
        member.signup(passwordService);

        return memberRepository.save(member).getId();
    }

    @Transactional(readOnly = true)
    public String login(MemberLoginServiceRequest request) {
        Member member = getByEmail(request.getEmail());
        member.login(request.getPassword(), passwordService);

        return tokenService.generate(member.getId().toString(), secret, offset);
    }

    private void verifyMember(MemberCreateServiceRequest request) {
        if(!request.verifyEmail()) {
            throw new IllegalArgumentException("이메일 형식이 올바르지 않습니다.");
        }

        if(!request.verifyPassword()) {
            throw new IllegalArgumentException("비밀번호 형식이 올바르지 않습니다.");
        }

        memberRepository.findByEmail(request.getEmail()).ifPresent((m) -> {
            throw new IllegalArgumentException("다른 이메일을 사용해주세요.");
        });
    }

    //FIXME: NotFoundException 변경
    private Member getByEmail(String email) {
        return memberRepository.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("이메일 또는 비밀번호가 일치하지 않습니다."));
    }
}
