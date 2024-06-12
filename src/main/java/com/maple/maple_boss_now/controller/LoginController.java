package com.maple.maple_boss_now.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    /**
     * 로그인 페이지 이동 Mvc컨트롤러
     *
     * @return login.html
     * @version 1.0
     * @since 2024-06-12
     */
    @GetMapping("/login")
    public String ShowLoginPage() {
        return "login"; // login.html will be rendered
    }

    /**
     * 회원가입 페이지 이동 Mvc컨트롤러
     *
     * @return signup.html
     * @version 1.0
     * @since 2024-06-12
     */
    @GetMapping("/signup")
    public String ShowSignUpPage() {
        return "signup";
    }
}
