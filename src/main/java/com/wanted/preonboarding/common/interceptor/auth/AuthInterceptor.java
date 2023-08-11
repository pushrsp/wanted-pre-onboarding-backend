package com.wanted.preonboarding.common.interceptor.auth;

import com.wanted.preonboarding.common.service.token.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RequiredArgsConstructor
public class AuthInterceptor implements HandlerInterceptor {
    private final TokenService tokenService;
    private final String secret;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(request.getMethod().equals("OPTIONS")) {
            return true;
        }

        String token = tokenService.extract(request);
        String memberId = tokenService.verify(token, secret);

        return StringUtils.hasText(memberId);
    }
}
