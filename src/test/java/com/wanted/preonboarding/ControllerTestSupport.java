package com.wanted.preonboarding;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wanted.preonboarding.article.controller.ArticleController;
import com.wanted.preonboarding.article.service.ArticleService;
import com.wanted.preonboarding.member.controller.MemberController;
import com.wanted.preonboarding.member.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = {MemberController.class, ArticleController.class})
public class ControllerTestSupport {
    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @MockBean
    protected MemberService memberService;

    @MockBean
    protected ArticleService articleService;
}
