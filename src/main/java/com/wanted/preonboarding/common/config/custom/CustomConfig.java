package com.wanted.preonboarding.common.config.custom;

import com.wanted.preonboarding.common.service.date.DateService;
import com.wanted.preonboarding.common.service.date.KrDateService;
import com.wanted.preonboarding.common.service.password.BcryptService;
import com.wanted.preonboarding.common.service.password.PasswordService;
import com.wanted.preonboarding.common.service.token.JwtService;
import com.wanted.preonboarding.common.service.token.TokenService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CustomConfig {
    @Bean
    public PasswordService passwordService() {
        return new BcryptService();
    }

    @Bean
    public TokenService tokenService() {
        return new JwtService();
    }

    @Bean
    public DateService dateService() {
        return new KrDateService();
    }
}
