package com.wanted.preonboarding.member.service;

import com.wanted.preonboarding.IntegrationTestSupport;
import com.wanted.preonboarding.member.domain.Member;
import com.wanted.preonboarding.member.infra.MemberRepository;
import com.wanted.preonboarding.member.service.request.MemberCreateServiceRequest;
import com.wanted.preonboarding.member.service.request.MemberLoginServiceRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

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
        memberService.save(request);

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
        IllegalArgumentException illegalArgumentException = catchThrowableOfType(() -> memberService.save(request), IllegalArgumentException.class);

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
        IllegalArgumentException illegalArgumentException = catchThrowableOfType(() -> memberService.save(request), IllegalArgumentException.class);

        //then
        assertThat(illegalArgumentException).isNotNull();
        assertThat(illegalArgumentException.getMessage()).isEqualTo("비밀번호 형식이 올바르지 않습니다.");
    }

    @DisplayName("중복된 이메일은 member를 생성할 수 없다.")
    @Test
    public void can_not_create_member_by_duplicate_email() throws Exception {
        //given
        MemberCreateServiceRequest r1 = createMemberCreateServiceRequest("fsa@naver.com", "password");
        MemberCreateServiceRequest r2 = createMemberCreateServiceRequest("fsa@naver.com", "password");

        memberService.save(r1);

        //when
        IllegalArgumentException illegalArgumentException = catchThrowableOfType(() -> memberService.save(r2), IllegalArgumentException.class);

        //then
        assertThat(illegalArgumentException).isNotNull();
        assertThat(illegalArgumentException.getMessage()).isEqualTo("다른 이메일을 사용해주세요.");
    }

    @DisplayName("MemberLoginServiceRequest를 통해 로그인을 할 수 있다.")
    @Test
    public void can_login_by_MemberLoginServiceRequest() throws Exception {
        //given
        MemberCreateServiceRequest memberCreateServiceRequest = createMemberCreateServiceRequest("abc@naver.com", "password");
        memberService.save(memberCreateServiceRequest);

        MemberLoginServiceRequest memberLoginServiceRequest = createMemberLoginServiceRequest("abc@naver.com", "password");

        //when
        String token = memberService.login(memberLoginServiceRequest);

        //then
        assertThat(token).isNotNull();
    }

    @DisplayName("이메일이 존재하지 않으면 로그인을 할 수 없다.")
    @Test
    public void can_not_login_by_non_existed_email() throws Exception {
        //given
        MemberCreateServiceRequest memberCreateServiceRequest = createMemberCreateServiceRequest("abc@naver.com", "password");
        memberService.save(memberCreateServiceRequest);

        MemberLoginServiceRequest memberLoginServiceRequest = createMemberLoginServiceRequest("abc21@naver.com", "password");

        //when
        IllegalArgumentException illegalArgumentException = catchThrowableOfType(() -> memberService.login(memberLoginServiceRequest), IllegalArgumentException.class);

        //then
        assertThat(illegalArgumentException).isNotNull();
        assertThat(illegalArgumentException.getMessage()).isEqualTo("이메일 또는 비밀번호가 일치하지 않습니다.");
    }

    @DisplayName("비밀번호가 다를 시 로그인을 할 수 없다.")
    @Test
    public void can_not_login_by_different_password() throws Exception {
        //given
        MemberCreateServiceRequest memberCreateServiceRequest = createMemberCreateServiceRequest("abc@naver.com", "password");
        memberService.save(memberCreateServiceRequest);

        MemberLoginServiceRequest memberLoginServiceRequest = createMemberLoginServiceRequest("abc@naver.com", "password32131");

        IllegalArgumentException illegalArgumentException = catchThrowableOfType(() -> memberService.login(memberLoginServiceRequest), IllegalArgumentException.class);

        //then
        assertThat(illegalArgumentException).isNotNull();
        assertThat(illegalArgumentException.getMessage()).isEqualTo("이메일 또는 비밀번호가 일치하지 않습니다.");
    }

    private MemberLoginServiceRequest createMemberLoginServiceRequest(String email, String password) {
        return MemberLoginServiceRequest.builder()
                .email(email)
                .password(password)
                .build();
    }

    private MemberCreateServiceRequest createMemberCreateServiceRequest(String email, String password) {
        return MemberCreateServiceRequest.builder()
                .email(email)
                .password(password)
                .build();
    }
}
