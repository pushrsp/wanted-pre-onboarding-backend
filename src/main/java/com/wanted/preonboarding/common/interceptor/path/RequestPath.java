package com.wanted.preonboarding.common.interceptor.path;

import lombok.Getter;

@Getter
public class RequestPath {
    private final String pathPattern;
    private final String method;

    public RequestPath(String pathPattern, String method) {
        this.pathPattern = pathPattern;
        this.method = method;
    }
}
