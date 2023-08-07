package com.wanted.preonboarding.user.infra;

import com.wanted.preonboarding.IntegrationTestSupport;
import com.wanted.preonboarding.user.domain.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

class UserRepositoryTest extends IntegrationTestSupport {
    @Autowired
    private UserRepository userRepository;

    @AfterEach
    void tearDown() {
        userRepository.deleteAllInBatch();
    }

    @DisplayName("UserDomain을 통해 User를 생성할 수 있다.")
    @Test
    public void can_save_user_by_user_domain() throws Exception {
        //given
        LocalDateTime now = LocalDateTime.of(2023, 8, 7, 17, 47);
        User userDomain = createUserDomain("abc@naver.com", "test", now);

        //when
        User savedUser = userRepository.save(userDomain);

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
        User userDomain = createUserDomain("abc@naver.com", "test", now);

        User savedUser = userRepository.save(userDomain);

        //when
        Optional<User> u1 = userRepository.findById(savedUser.getId());
        Optional<User> u2 = userRepository.findById(0L);

        //then
        assertThat(u1.isPresent()).isTrue();
        assertThat(u2.isPresent()).isFalse();
    }

    private User createUserDomain(String email, String password, LocalDateTime createdTime) {
        return User.builder()
                .email(email)
                .password(password)
                .createdTime(createdTime)
                .build();
    }
}
