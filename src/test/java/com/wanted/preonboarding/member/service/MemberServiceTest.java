package com.wanted.preonboarding.member.service;

import com.wanted.preonboarding.IntegrationTestSupport;
import com.wanted.preonboarding.member.domain.Member;
import com.wanted.preonboarding.member.infra.MemberRepository;
import com.wanted.preonboarding.member.service.request.MemberCreateServiceRequest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class MemberServiceTest extends IntegrationTestSupport {
    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberRepository memberRepository;

    @AfterEach
    void tearDown() {
        memberRepository.deleteAllInBatch();
    }

    @DisplayName("MemberCreateServiceRequest를 통해 member를 생성할 수 있다.")
    @Test
    public void can_create_member_by_MemberCreateServiceRequest() throws Exception {
        //given
        MemberCreateServiceRequest request = createMemberCreateServiceRequest("abc@naver.com", "password");

        //when
        memberService.create(request);

        //then
        Optional<Member> optionalMember = memberRepository.findByEmail(request.getEmail());

        assertThat(optionalMember.isPresent()).isTrue();
    }

    @DisplayName("잘못된 이메일은 member를 생성할 수 없다.")
    @Test
    public void can_not_create_member_by_wrong_email() throws Exception {
        //given
        MemberCreateServiceRequest request = createMemberCreateServiceRequest("fdsafsda", "password");

        //when
        IllegalArgumentException illegalArgumentException = catchThrowableOfType(() -> memberService.create(request), IllegalArgumentException.class);

        //then
        assertThat(illegalArgumentException).isNotNull();
        assertThat(illegalArgumentException.getMessage()).isEqualTo("이메일 형식이 올바르지 않습니다.");
    }

    @DisplayName("잘못된 비밀번호는 member를 생성할 수 없다.")
    @Test
    public void can_not_create_member_by_wrong_password() throws Exception {
        //given
        MemberCreateServiceRequest request = createMemberCreateServiceRequest("fsa@naver.com", "123");

        //when
        IllegalArgumentException illegalArgumentException = catchThrowableOfType(() -> memberService.create(request), IllegalArgumentException.class);

        //then
        assertThat(illegalArgumentException).isNotNull();
        assertThat(illegalArgumentException.getMessage()).isEqualTo("비밀번호 형식이 올바르지 않습니다.");
    }

    private MemberCreateServiceRequest createMemberCreateServiceRequest(String email, String password) {
        return MemberCreateServiceRequest.builder()
                .email(email)
                .password(password)
                .build();
    }
}
