package com.wanted.preonboarding.user.infra;

import com.wanted.preonboarding.user.domain.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;

class UserEntityTest {
    @DisplayName("UserEntity는 UserDomain으로 부터 생성할 수 있다.")
    @Test
    public void can_create_user_entity_from_user_domain() throws Exception {
        //given
        LocalDateTime createdTime = LocalDateTime.of(2023, 8, 7, 13, 57);
        User userDomain = createUserDomain(null, "abc@naver.com", "password", createdTime);

        //when
        UserEntity userEntity = UserEntity.from(userDomain);

        //then
        assertThat(userEntity.getId()).isNull();
        assertThat(userEntity.getEmail()).isEqualTo(userDomain.getEmail());
        assertThat(userEntity.getPassword()).isEqualTo(userDomain.getPassword());
        assertThat(userEntity.getCreatedTime()).isEqualTo(userDomain.getCreatedTime());
    }

    @DisplayName("UserEntity는 UserDomain으로 컨버팅 할 수 있다.")
    @Test
    public void user_entity_can_convert_to_user_domain() throws Exception {
        //given
        LocalDateTime createdTime = LocalDateTime.of(2023, 8, 7, 13, 57);
        UserEntity userEntity = UserEntity.from(createUserDomain(1L, "test@naver.com", "password1234", createdTime));

        //when
        User userDomain = userEntity.toDomain();

        //then
        assertThat(userDomain.getId()).isEqualTo(userEntity.getId());
        assertThat(userDomain.getEmail()).isEqualTo(userEntity.getEmail());
        assertThat(userDomain.getPassword()).isEqualTo(userEntity.getPassword());
        assertThat(userDomain.getCreatedTime()).isEqualTo(userEntity.getCreatedTime());
    }

    private User createUserDomain(Long id, String email, String password, LocalDateTime createdTime) {
        return User.builder()
                .id(id)
                .email(email)
                .password(password)
                .createdTime(createdTime)
                .build();
    }
}
