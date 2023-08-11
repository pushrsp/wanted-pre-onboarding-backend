package com.wanted.preonboarding.common.config.web;

import com.wanted.preonboarding.common.config.argumentresolver.AuthArgumentResolver;
import com.wanted.preonboarding.common.interceptor.auth.AuthInterceptor;
import com.wanted.preonboarding.common.interceptor.path.PathMatcherInterceptor;
import com.wanted.preonboarding.common.service.token.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {
    private final TokenService tokenService;

    @Value("${token.secret}")
    private String secret;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**");
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new AuthArgumentResolver(secret, tokenService));
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authInterceptor())
                .order(1)
                .addPathPatterns("/api/**");
    }

    private HandlerInterceptor authInterceptor() {
        PathMatcherInterceptor pathMatcherInterceptor = new PathMatcherInterceptor(new AuthInterceptor(tokenService, secret));
        pathMatcherInterceptor
                .excludePathPattern("/api/members/**", "POST")
                .excludePathPattern("/api/members/**", "GET")
                .includePathPattern("/api/articles/**", "POST")
                .includePathPattern("/api/articles/**", "PATCH")
                .includePathPattern("/api/articles/**", "DELETE");

        return pathMatcherInterceptor;
    }
}
