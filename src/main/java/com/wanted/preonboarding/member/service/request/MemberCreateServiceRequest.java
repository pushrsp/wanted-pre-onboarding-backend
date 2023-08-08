package com.wanted.preonboarding.member.service.request;

import com.wanted.preonboarding.common.service.date.DateService;
import com.wanted.preonboarding.member.domain.Member;
import lombok.Builder;
import lombok.Getter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Getter
public class MemberCreateServiceRequest {
    private static final String EMAIL_REGEX = "^[_a-z0-9-]+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+$";
    private static final int PASSWORD_MIN_LEN = 8;
    private static final int PASSWORD_MAX_LEN = 100;

    private String email;
    private String password;

    @Builder
    private MemberCreateServiceRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public boolean verifyEmail() {
        Pattern p = Pattern.compile(EMAIL_REGEX);
        Matcher m = p.matcher(this.email);

        return !m.matches();
    }

    public boolean verifyPassword() {
        return PASSWORD_MIN_LEN <= this.password.length() && this.password.length() <= PASSWORD_MAX_LEN;
    }

    public Member toDomain(DateService dateService) {
        return Member.builder()
                .email(email)
                .password(password)
                .createdTime(dateService.getDateTime())
                .build();
    }
}
