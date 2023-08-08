package com.wanted.preonboarding.member.service.request;

import lombok.Builder;
import lombok.Getter;

@Getter
public class MemberLoginServiceRequest {
    private String email;
    private String password;

    @Builder
    private MemberLoginServiceRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
