package com.wanted.preonboarding.member.domain;

import com.wanted.preonboarding.common.service.password.PasswordService;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class Member {
    private String id;
    private String email;
    private String password;
    private LocalDateTime createdTime;

    @Builder
    private Member(String id, String email, String password, LocalDateTime createdTime) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.createdTime = createdTime;
    }

    public void signup(PasswordService passwordService) {
        this.password = passwordService.hash(this.password);
    }

    public void login(String plainPassword, PasswordService passwordService) {
        if(!passwordService.verify(plainPassword, this.password)) {
            throw new IllegalArgumentException("이메일 또는 비밀번호가 일치하지 않습니다.");
        }
    }

    public void changeEmail(String email) {
        this.email = email;
    }
}
