package com.i5.ds.User;

import com.i5.ds.User.User;
import com.i5.ds.User.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/register")
    public String registerPage() {
        return "pages/board/list";
    }

    @PostMapping("/register")
    public String register(@RequestParam String userId,
                           @RequestParam String userPw,
                           @RequestParam String userName,
                           @RequestParam String userEmail,
                           Model model) {
        if (userService.isUserExist(userId)) {
            model.addAttribute("error", "User already exists!");
            return "register";
        }
        userService.registerUser(userId, userPw, userName, userEmail);
        return "pages/board/list";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "pages/board/list";
    }
}
