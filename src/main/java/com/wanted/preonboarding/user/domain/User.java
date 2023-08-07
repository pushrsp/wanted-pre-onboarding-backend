package com.wanted.preonboarding.user.domain;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class User {
    private Long id;
    private String email;
    private String password;
    private LocalDateTime createdTime;

    @Builder
    private User(Long id, String email, String password, LocalDateTime createdTime) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.createdTime = createdTime;
    }

    public void validate() {
        //TODO
    }

    public void login(String username, String password) {
        //TODO
    }
}
