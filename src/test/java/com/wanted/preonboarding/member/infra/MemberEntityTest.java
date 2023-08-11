package com.wanted.preonboarding.member.infra;

import com.wanted.preonboarding.member.domain.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;

class MemberEntityTest {
    @DisplayName("MemberEntity는 MemberDomain으로 부터 생성할 수 있다.")
    @Test
    public void can_create_user_entity_from_user_domain() throws Exception {
        //given
        LocalDateTime createdTime = LocalDateTime.of(2023, 8, 7, 13, 57);
        Member memberDomain = createMemberDomain(null, "abc@naver.com", "password", createdTime);

        //when
        MemberEntity memberEntity = MemberEntity.from(memberDomain);

        //then
        assertThat(memberEntity.getId()).isNull();
        assertThat(memberEntity.getEmail()).isEqualTo(memberDomain.getEmail());
        assertThat(memberEntity.getPassword()).isEqualTo(memberDomain.getPassword());
        assertThat(memberEntity.getCreatedTime()).isEqualTo(memberDomain.getCreatedTime());
    }

    @DisplayName("MemberEntity는 MemberDomain으로 컨버팅 할 수 있다.")
    @Test
    public void user_entity_can_convert_to_user_domain() throws Exception {
        //given
        LocalDateTime createdTime = LocalDateTime.of(2023, 8, 7, 13, 57);
        MemberEntity memberEntity = MemberEntity.from(createMemberDomain("1", "test@naver.com", "password1234", createdTime));

        //when
        Member memberDomain = memberEntity.toDomain();

        //then
        assertThat(memberDomain.getId()).isEqualTo(String.valueOf(memberEntity.getId()));
        assertThat(memberDomain.getEmail()).isEqualTo(memberEntity.getEmail());
        assertThat(memberDomain.getPassword()).isEqualTo(memberEntity.getPassword());
        assertThat(memberDomain.getCreatedTime()).isEqualTo(memberEntity.getCreatedTime());
    }

    private Member createMemberDomain(String id, String email, String password, LocalDateTime createdTime) {
        return Member.builder()
                .id(id)
                .email(email)
                .password(password)
                .createdTime(createdTime)
                .build();
    }
}
