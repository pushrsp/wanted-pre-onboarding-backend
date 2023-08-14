package com.wanted.preonboarding.common.controller;

import com.wanted.preonboarding.common.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ServletExceptionController {
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @GetMapping("/exception/404")
    public ApiResponse exception404() {
        return ApiResponse.of(null, "해당 페이지가 존재하지 않습니다.", false);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @GetMapping("/exception/500")
    public ApiResponse exception500() {
        return ApiResponse.of(null, "서버에 문제가 생겼습니다. 관리자에게 문의해주세요.", false);
    }
}
