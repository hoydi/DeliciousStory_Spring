package com.i5.ds.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Optional;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 로그인 페이지 표시
     *
     * @return 로그인 페이지 뷰
     */
    @GetMapping("/login")
    public String loginPage() {
        return "pages/user/signIn";
    }

    /**
     * 사용자 등록 페이지 표시
     *
     * @return 사용자 등록 페이지 뷰
     */
    @GetMapping("/register")
    public String registerPage() {
        return "pages/user/signUp";
    }

    /**
     * 사용자 등록 처리
     *
     * @param userId    사용자 ID
     * @param userPw    사용자 비밀번호
     * @param userName  사용자 이름
     * @param userEmail 사용자 이메일
     * @return 로그인 페이지로 리다이렉트
     */
    @PostMapping("/register")
    public String registerUser(@RequestParam String userId,
                               @RequestParam String userPw,
                               @RequestParam String userName,
                               @RequestParam String userEmail
    ) {
        userService.registerUser(userId, userPw, userName, userEmail);
        return "redirect:/login";
    }


    @GetMapping("/mypage")
    public String updatePage(Model model) {
        // 현재 사용자 정보를 가져오는 로직
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserId = authentication.getName(); // 현재 로그인한 사용자 ID를 가져옴

        // 사용자 정보를 DB에서 가져오기
        Optional<User> userOpt = userService.findByUserId(currentUserId);
        if (userOpt.isPresent()) {
            model.addAttribute("user", userOpt.get());
        } else {
            // 사용자 정보가 없을 경우 처리 (예: 에러 메시지 추가)
            model.addAttribute("error", "User not found.");
        }
        System.out.println("User: " + userOpt.get());
        System.out.println("User: " + userOpt.get().getUserId());
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        return "pages/user/mypage_info"; // Thymeleaf 템플릿 파일의 경로
    }

    /**
     * 사용자 정보 업데이트 처리
     *
     * @param userId    사용자 ID
     * @param userName  사용자 이름
     * @param userEmail 사용자 이메일
     * @param userPw    사용자비번
     * @return 사용자 정보 업데이트 완료 페이지로 리다이렉트
     */
    @PostMapping("/update")
    public String updateUser(@RequestParam String userId,
                             @RequestParam String userName,
                             @RequestParam String userEmail,
                             @RequestParam String userPw) {
        userService.updateUser(userId, userName, userEmail, userPw);
        return "redirect:/update";
    }
}
