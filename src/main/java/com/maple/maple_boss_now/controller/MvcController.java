package com.maple.maple_boss_now.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MvcController {

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

    /**
     * 메인 페이지 이동 Mvc컨트롤러
     *
     * @return index.html
     * @version 1.0
     * @since 2024-06-14
     */
    @GetMapping("/")
    public String ShowIndexPage() {
        return "index";
    }
    /**
     * 보스 목록 페이지 이동 Mvc컨트롤러
     *
     * @return boss.html
     * @version 1.0
     * @since 2024-06-14
     */
    @GetMapping("/boss")
    public String ShowBossPage() {
        return "boss";
    }
    /**
        * 매칭 페이지 이동 Mvc컨트롤러
        * @return match.html
        * @version 1.0
        * @since 2024-06-17
     */
    @GetMapping("/match")
    public String ShowMatchPage() {
        return "match";
    }
}
