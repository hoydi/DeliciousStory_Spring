package com.i5.ds.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
public class UserController {

    @Autowired
    private PasswordEncoder passwordEncoder;


    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    // 회원가입 페이지 이동
    @GetMapping("/signup")
    public String signupPage() {
        return "pages/user/signup"; // 뷰 파일 이름
    }

    // 회원가입 처리
    @PostMapping("/signup")
    public String signup(@RequestParam String userId,
                         @RequestParam String userPw,
                         @RequestParam String userName,
                         @RequestParam String userEmail,
                         Model model) {
        if (userService.isUserExist(userId)) {
            model.addAttribute("error", "User already exists!");
            return "pages/user/signup";
        }
        String encodedPassword = passwordEncoder.encode(userPw);
        userService.registerUser(userId, encodedPassword, userName, userEmail);
        return "redirect:/signin";
    }

    // 로그인 페이지 이동
    @GetMapping("/signin")
    public String signinPage() {
        return "pages/user/signin"; // 뷰 파일 이름
    }

    // 마이페이지 이동
    @GetMapping("/mypage")
    public String myPage(Model model, Principal principal) {
        User user = null;

        // 현재 로그인된 사용자의 정보를 가져옴
        if (principal != null) {
            String userId = principal.getName();
            user = userService.findUserById(userId);
        }

        // 모델에 사용자 정보 추가
        model.addAttribute("user", user);

        return "pages/user/mypage"; // 뷰 파일 이름
    }

    // 마이페이지에서 정보 수정 처리
    @PostMapping("/mypage")
    public String updateUser(@RequestParam String userEmail,
                             @RequestParam String userPw,
                             Principal principal) {
        String userId = principal.getName();
        userService.updateUserInfo(userId, userEmail, userPw);
        return "redirect:/mypage";
    }
}
