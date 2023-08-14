package com.wanted.preonboarding.member.infra;

import com.wanted.preonboarding.IntegrationTestSupport;
import com.wanted.preonboarding.member.domain.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.*;

class MemberRepositoryTest extends IntegrationTestSupport {
    @Autowired
    private MemberRepository memberRepository;

    @DisplayName("MemberDomain을 통해 member를 생성할 수 있다.")
    @Test
    public void can_save_member_by_member_domain() throws Exception {
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

    @DisplayName("UserId를 통해 Member를 찾을 수 있다.")
    @Test
    public void can_find_member_by_id() throws Exception {
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

    @DisplayName("Email을 통해 Member를 찾을 수 있다.")
    @Test
    public void can_find_member_by_email() throws Exception {
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

    @DisplayName("mebmerIds를 통해 member들을 찾을 수 있다.")
    @ParameterizedTest
    @MethodSource("provideMemberIds")
    public void can_find_members_by_ids(int size, List<Long> memberIds, int expected) throws Exception {
        //given
        for (int i = 0; i < size; i++) {
            memberRepository.save(createMemberDomain("test" + i + "@naver.com", "password", LocalDateTime.now()));
        }

        //when
        List<Member> members = memberRepository.findAllByIdIn(memberIds);

        //then
        assertThat(members).hasSize(expected);
    }

    private static Stream<Arguments> provideMemberIds() {
        return Stream.of(
                Arguments.of(10 ,List.of(1L, 1L, 2L, 3L, 4L), 4),
                Arguments.of(10 ,List.of(1L, 1L, 1L, 1L), 1),
                Arguments.of(5 ,List.of(1L, 2L, 3L, 4L, 5L), 5),
                Arguments.of(5 ,List.of(), 0)
        );
    }

    private Member createMemberDomain(String email, String password, LocalDateTime createdTime) {
        return Member.builder()
                .email(email)
                .password(password)
                .createdTime(createdTime)
                .build();
    }
}
