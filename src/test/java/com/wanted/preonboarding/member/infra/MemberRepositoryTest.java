package com.wanted.preonboarding.member.infra;

import com.wanted.preonboarding.IntegrationTestSupport;
import com.wanted.preonboarding.member.domain.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

class MemberRepositoryTest extends IntegrationTestSupport {
    @Autowired
    private MemberRepository memberRepository;

    @DisplayName("UserDomain을 통해 User를 생성할 수 있다.")
    @Test
    public void can_save_user_by_user_domain() throws Exception {
        //given
        LocalDateTime now = LocalDateTime.of(2023, 8, 7, 17, 47);
        Member memberDomain = createMemberDomain("test@naver.com", "test", now);

        //when
        Member savedUser = memberRepository.save(memberDomain);

        //then
        assertThat(savedUser.getId()).isNotNull();
        assertThat(savedUser.getEmail()).isEqualTo(memberDomain.getEmail());
        assertThat(savedUser.getPassword()).isEqualTo(memberDomain.getPassword());
        assertThat(savedUser.getCreatedTime()).isEqualTo(memberDomain.getCreatedTime());
    }

    @DisplayName("UserId를 통해 User를 찾을 수 있다.")
    @Test
    public void can_find_user_by_id() throws Exception {
        //given
        LocalDateTime now = LocalDateTime.of(2023, 8, 7, 17, 47);
        Member memberDomain = createMemberDomain("abc@naver.com", "test", now);

        Member savedMember = memberRepository.save(memberDomain);

        //when
        Optional<Member> u1 = memberRepository.findById(savedMember.getId());
        Optional<Member> u2 = memberRepository.findById("0");

        //then
        assertThat(u1.isPresent()).isTrue();
        assertThat(u2.isPresent()).isFalse();
    }

    @DisplayName("Email을 통해 User를 찾을 수 있다.")
    @Test
    public void can_find_user_by_email() throws Exception {
        //given
        String email = "addd@naver.com";
        LocalDateTime now = LocalDateTime.of(2023, 8, 7, 17, 47);
        Member memberDomain = createMemberDomain(email, "test", now);

        memberRepository.save(memberDomain);

        //when
        Optional<Member> findUser = memberRepository.findByEmail(email);

        //then
        assertThat(findUser.isPresent()).isTrue();
    }

    private Member createMemberDomain(String email, String password, LocalDateTime createdTime) {
        return Member.builder()
                .email(email)
                .password(password)
                .createdTime(createdTime)
                .build();
    }
}
