package com.wanted.preonboarding.member.controller;

import com.wanted.preonboarding.common.response.ApiResponse;
import com.wanted.preonboarding.member.controller.request.MemberCreateControllerRequest;
import com.wanted.preonboarding.member.controller.request.MemberLoginControllerRequest;
import com.wanted.preonboarding.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @PostMapping("/api/members/signup")
    public ApiResponse<String> signup(@Valid @RequestBody MemberCreateControllerRequest request) {
        return ApiResponse.ok(memberService.save(request.toServiceRequest()));
    }

    @PostMapping("/api/members/login")
    public ApiResponse<String> login(@Valid @RequestBody MemberLoginControllerRequest request) {
        return ApiResponse.ok(memberService.login(request.toServiceRequest()));
    }
}
