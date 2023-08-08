package com.wanted.preonboarding.member.domain;

import com.wanted.preonboarding.common.service.password.PasswordService;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class Member {
    private Long id;
    private String email;
    private String password;
    private LocalDateTime createdTime;

    @Builder
    private Member(Long id, String email, String password, LocalDateTime createdTime) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.createdTime = createdTime;
    }

    public void signup(PasswordService passwordService) {
        this.password = passwordService.hash(this.password);
    }

    public void login(String username, String password) {
        //TODO
    }
}
