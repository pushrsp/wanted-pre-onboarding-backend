package com.wanted.preonboarding.member.controller.request;

import com.wanted.preonboarding.member.service.request.MemberLoginServiceRequest;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
public class MemberLoginControllerRequest {
    @NotBlank(message = "이메일 또는 비밀번호가 일치하지 않습니다.")
    private String email;

    @NotBlank(message = "이메일 또는 비밀번호가 일치하지 않습니다.")
    private String password;

    @Builder
    private MemberLoginControllerRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public MemberLoginServiceRequest toServiceRequest() {
        return MemberLoginServiceRequest.builder()
                .email(this.email)
                .password(this.password)
                .build();
    }
}
