package com.wanted.preonboarding.common.interceptor.path;

import lombok.RequiredArgsConstructor;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class PathMatcherInterceptor implements HandlerInterceptor {
    private final HandlerInterceptor handlerInterceptor;
    private final PathContainer pathContainer;

    public PathMatcherInterceptor(HandlerInterceptor handlerInterceptor) {
        this.handlerInterceptor = handlerInterceptor;
        this.pathContainer = new PathContainer();
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(pathContainer.isExcluded(request.getServletPath(), request.getMethod())) {
            return true;
        }

        return handlerInterceptor.preHandle(request, response, handler);
    }

    public PathMatcherInterceptor includePathPattern(String pathPattern, String method) {
        pathContainer.includePathPattern(pathPattern, method);
        return this;
    }

    public PathMatcherInterceptor excludePathPattern(String pathPattern, String method) {
        pathContainer.excludePathPattern(pathPattern, method);
        return this;
    }
}
