package com.wanted.preonboarding.member.controller.request;

import com.wanted.preonboarding.member.service.request.MemberCreateServiceRequest;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
public class MemberCreateControllerRequest {
    @NotBlank(message = "이메일을 입력해주세요.")
    private String email;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    private String password;

    @Builder
    private MemberCreateControllerRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public MemberCreateServiceRequest toServiceRequest() {
        return MemberCreateServiceRequest.builder()
                .email(this.email)
                .password(this.password)
                .build();
    }
}
