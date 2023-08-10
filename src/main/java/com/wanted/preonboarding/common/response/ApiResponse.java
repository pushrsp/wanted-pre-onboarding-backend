package com.wanted.preonboarding.common.response;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {
    private T data;
    private String message;
    private boolean success;

    private ApiResponse(T data, String message, boolean success) {
        this.data = data;
        this.message = message;
        this.success = success;
    }

    public static <T> ApiResponse<T> of(T data, String message, boolean success) {
        return new ApiResponse<>(data, message, success);
    }

    public static <T> ApiResponse<T> ok(T data) {
        return new ApiResponse<>(data, null, true);
    }
}
