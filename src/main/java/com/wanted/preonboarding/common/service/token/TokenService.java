package com.wanted.preonboarding.common.service.token;

public interface TokenService {
    String generate(String data, String secret, Long offset);
    String extract();
    String get();
}
