package com.wanted.preonboarding.common.service.token;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import java.util.Date;

public class JwtService implements TokenService {
    @Override
    public String generate(String data, String secret, Long offset) {
        if(offset <= 0) {
            throw new IllegalArgumentException("offset 0보다 커야 됩니다.");
        }

        return JWT.create()
                .withClaim("data", data)
                .withExpiresAt(new Date(System.currentTimeMillis() + offset))
                .sign(Algorithm.HMAC256(secret));
    }

    @Override
    public String extract() {
        return null;
    }

    @Override
    public String get() {
        return null;
    }
}
