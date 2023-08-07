package com.wanted.preonboarding.member.infra;

import com.wanted.preonboarding.IntegrationTestSupport;
import com.wanted.preonboarding.member.domain.Member;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

class MemberRepositoryTest extends IntegrationTestSupport {
    @Autowired
    private MemberRepository memberRepository;

    @AfterEach
    void tearDown() {
        memberRepository.deleteAllInBatch();
    }

    @DisplayName("UserDomain을 통해 User를 생성할 수 있다.")
    @Test
    public void can_save_user_by_user_domain() throws Exception {
        //given
        LocalDateTime now = LocalDateTime.of(2023, 8, 7, 17, 47);
        Member userDomain = createUserDomain("abc@naver.com", "test", now);

        //when
        Member savedUser = memberRepository.save(userDomain);

        //then
        assertThat(savedUser.getId()).isNotNull();
        assertThat(savedUser.getEmail()).isEqualTo(userDomain.getEmail());
        assertThat(savedUser.getPassword()).isEqualTo(userDomain.getPassword());
        assertThat(savedUser.getCreatedTime()).isEqualTo(userDomain.getCreatedTime());
    }

    @DisplayName("UserId를 통해 User를 찾을 수 있다.")
    @Test
    public void can_find_user_by_id() throws Exception {
        //given
        LocalDateTime now = LocalDateTime.of(2023, 8, 7, 17, 47);
        Member userDomain = createUserDomain("abc@naver.com", "test", now);

        Member savedUser = memberRepository.save(userDomain);

        //when
        Optional<Member> u1 = memberRepository.findById(savedUser.getId());
        Optional<Member> u2 = memberRepository.findById(0L);

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
        Member userDomain = createUserDomain(email, "test", now);

        memberRepository.save(userDomain);

        //when
        Optional<Member> findUser = memberRepository.findByEmail(email);

        //then
        assertThat(findUser.isPresent()).isTrue();
    }

    private Member createUserDomain(String email, String password, LocalDateTime createdTime) {
        return Member.builder()
                .email(email)
                .password(password)
                .createdTime(createdTime)
                .build();
    }
}
