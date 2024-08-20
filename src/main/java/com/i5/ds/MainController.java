package com.i5.ds;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
    @GetMapping("/")
    public String index() {
        return "index";
    }

//    page 폴더 안의 login.html
    @GetMapping("/login")
    public String login(){
        return "page/login";
    }

}
