package com.wanted.preonboarding.common.interceptor.path;

import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import java.util.ArrayList;
import java.util.List;

public class PathContainer {
    private final PathMatcher pathMatcher;
    private final List<RequestPath> includePathPattern;
    private final List<RequestPath> excludePathPattern;

    public PathContainer() {
        this.pathMatcher = new AntPathMatcher();
        this.includePathPattern = new ArrayList<>();
        this.excludePathPattern = new ArrayList<>();
    }

    public boolean isExcluded(String targetPath, String targetMethod) {
        boolean included = includePathPattern.stream()
                .anyMatch(requestPath -> anyMatchPathPattern(targetPath, targetMethod, requestPath));

        boolean excluded = excludePathPattern.stream()
                .anyMatch(requestPath -> anyMatchPathPattern(targetPath, targetMethod, requestPath));

        return excluded || !included;
    }

    private boolean anyMatchPathPattern(String targetPath, String targetMethod, RequestPath requestPath) {
        return pathMatcher.match(requestPath.getPathPattern(), targetPath) && requestPath.getMethod().equals(targetMethod);
    }

    public void includePathPattern(String pathPattern, String method) {
        this.includePathPattern.add(new RequestPath(pathPattern, method));
    }

    public void excludePathPattern(String pathPattern, String method) {
        this.excludePathPattern.add(new RequestPath(pathPattern, method));
    }
}
