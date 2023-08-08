package com.wanted.preonboarding.member.service.request;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.*;

class MemberCreateServiceRequestTest {
    @DisplayName("주어진 입력을 통해 이메을 검증을 할 수 있다.")
    @ParameterizedTest
    @MethodSource("provideEmail")
    public void can_detect_email_is_right(String email, boolean expected) throws Exception {
        //given
        MemberCreateServiceRequest request = MemberCreateServiceRequest.builder()
                .email(email)
                .build();

        //when
        boolean result = request.verifyEmail();

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
}
