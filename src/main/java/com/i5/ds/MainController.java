package com.i5.ds;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
    @GetMapping("/")
    public String index() {
        return "index";
    }

    // 로그인
    @GetMapping("/signIn")
    public String signIn() {
        return "pages/signIn";
    }

    //회원가입
    @GetMapping("/signUp")
    public String signUp() {
        return "pages/signIn";
    }
}
