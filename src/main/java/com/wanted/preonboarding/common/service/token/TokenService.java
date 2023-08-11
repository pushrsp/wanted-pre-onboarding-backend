package com.wanted.preonboarding.common.service.token;

import javax.servlet.http.HttpServletRequest;

public interface TokenService {
    String generate(String data, String secret, Long offset);
    String extract(HttpServletRequest request);
    String verify(String token, String secret);
}
