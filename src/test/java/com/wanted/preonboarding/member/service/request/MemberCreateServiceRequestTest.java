package com.wanted.preonboarding.member.service.request;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.*;

class MemberCreateServiceRequestTest {
    @DisplayName("주어진 이메일을 통해 이메을 검증을 할 수 있다.")
    @ParameterizedTest
    @MethodSource("provideEmail")
    public void can_detect_email_is_valid(String email, boolean expected) throws Exception {
        //given
        MemberCreateServiceRequest request = MemberCreateServiceRequest.builder()
                .email(email)
                .build();

        //when
        boolean result = request.verifyEmail();

        //then
        assertThat(result).isEqualTo(expected);
    }

    @DisplayName("주어진 비밀번호를 통해 비밀번호 검증을 할 수 있다.")
    @ParameterizedTest
    @MethodSource("providerEmail")
    public void can_detect_password_length_is_valid(String password, boolean expected) throws Exception {
        //given
        MemberCreateServiceRequest request = MemberCreateServiceRequest.builder()
                .password(password)
                .build();

        //when
        boolean result = request.verifyPassword();

        //then
        assertThat(result).isEqualTo(expected);
    }

    private static Stream<Arguments> provideEmail() {
        return Stream.of(
                Arguments.of("abc@naver.com", true),
                Arguments.of("abc@naver", false),
                Arguments.of("@vxzxzxxz", false),
                Arguments.of("3421421@vcxz.321", true)
        );
    }

    private static Stream<Arguments> providerEmail() {
        return Stream.of(
                Arguments.of("12345678", true),
                Arguments.of("1234567", false),
                Arguments.of(null, false),
                Arguments.of("", false)
        );
    }
}
