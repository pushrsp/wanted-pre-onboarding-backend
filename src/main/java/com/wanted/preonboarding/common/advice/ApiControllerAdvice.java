package com.wanted.preonboarding.common.advice;

import com.wanted.preonboarding.common.exception.ExpiredException;
import com.wanted.preonboarding.common.exception.NoResourceFoundException;
import com.wanted.preonboarding.common.exception.WrongIdFormatException;
import com.wanted.preonboarding.common.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import org.springframework.validation.BindException;

@RestControllerAdvice
public class ApiControllerAdvice {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(WrongIdFormatException.class)
    public ApiResponse<Object> wrongIdFormatException(WrongIdFormatException e) {
        return fail(e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalStateException.class)
    public ApiResponse<Object> illegalStateException(IllegalStateException e) {
        return fail(e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BindException.class)
    public ApiResponse<Object> bindException(BindException e) {
        return fail(e.getBindingResult().getAllErrors().get(0).getDefaultMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public ApiResponse<Object> illegalArgumentException(IllegalArgumentException e) {
        return fail(e.getMessage());
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoResourceFoundException.class)
    public ApiResponse<Object> noResourceFoundException(NoResourceFoundException e) {
        return fail(e.getMessage());
    }

    @ResponseStatus(HttpStatus.GONE)
    @ExceptionHandler(ExpiredException.class)
    public ApiResponse<Object> expiredException(ExpiredException e) {
        return fail(e.getMessage());
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ApiResponse<Object> exception(Exception e) {
        return fail("서버에 문제가 생겼습니다. 관리자에게 문의해주세요.");
    }

    private ApiResponse<Object> fail(String message) {
        return ApiResponse.of(null, message, false);
    }
}
