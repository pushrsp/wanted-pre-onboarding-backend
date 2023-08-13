package com.wanted.preonboarding.member.controller;

import com.wanted.preonboarding.ControllerTestSupport;
import com.wanted.preonboarding.member.controller.request.MemberCreateControllerRequest;
import com.wanted.preonboarding.member.controller.request.MemberLoginControllerRequest;
import com.wanted.preonboarding.member.service.request.MemberLoginServiceRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.http.MediaType;

import java.util.stream.Stream;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class MemberControllerTest extends ControllerTestSupport {
    @DisplayName("MemberCreateControllerRequest를 통해 member를 등록할 수 있다.")
    @Test
    public void can_create_member_by_MemberCreateControllerRequest() throws Exception {
        //given
        MemberCreateControllerRequest request = MemberCreateControllerRequest.builder()
                .email("abc@naver.com")
                .password("password")
                .build();
        given(memberService.save(any()))
                .willReturn("1");

        //when then
        mockMvc.perform(post("/api/members/signup")
                        .content(objectMapper.writeValueAsBytes(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value("true"))
                .andExpect(jsonPath("$.data").isString());
    }

    @ParameterizedTest
    @MethodSource("provideEmailAndPassword")
    public void email_and_password_should_not_be_blank_when_signup(String email, String password, String expected) throws Exception {
        //given
        MemberCreateControllerRequest request = MemberCreateControllerRequest.builder()
                .email(email)
                .password(password)
                .build();

        //when then
        mockMvc.perform(post("/api/members/signup")
                        .content(objectMapper.writeValueAsBytes(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message").value(expected));
    }

    @DisplayName("MemberLoginControllerRequest를 통해 로그인을 할 수 있다.")
    @Test
    public void can_login_by_MemberLoginControllerRequest() throws Exception {
        //given
        MemberLoginControllerRequest request = MemberLoginControllerRequest.builder()
                .email("abc@naver.com")
                .password("password")
                .build();

        given(memberService.login(any(MemberLoginServiceRequest.class)))
                        .willReturn("token");

        //when then
        mockMvc.perform(post("/api/members/login")
                        .content(objectMapper.writeValueAsBytes(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value("true"))
                .andExpect(jsonPath("$.data").isString());
    }

    @ParameterizedTest
    @MethodSource("provideEmailAndPassword")
    public void email_and_password_should_not_be_blank_when_login(String email, String password, String expected) throws Exception {
        //given
        MemberLoginControllerRequest request = MemberLoginControllerRequest.builder()
                .email(email)
                .password(password)
                .build();

        //when then
        mockMvc.perform(post("/api/members/login")
                        .content(objectMapper.writeValueAsBytes(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message").value(expected));
    }

    private static Stream<Arguments> provideEmailAndPassword() {
        return Stream.of(
                Arguments.of(null, "password", "이메일을 입력해주세요."),
                Arguments.of("email", null, "비밀번호를 입력해주세요.")
        );
    }
}
