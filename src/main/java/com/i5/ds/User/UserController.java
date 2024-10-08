package com.i5.ds.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.i5.ds.Board.Board;
import com.i5.ds.Board.BoardService;
import com.i5.ds.Recipe.SiteRecipe.LikeService;
import com.i5.ds.Recipe.SiteRecipe.Recipe;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
public class UserController {

	@Autowired
	private BoardService boardService;
	@Autowired
	private UserService userService;
	@Autowired
	private LikeService likeService;
	@Autowired
	private LikeService recipeService;
	@Autowired
	private PasswordEncoder passwordEncoder;

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
	public String registerUser(@RequestParam String userId, @RequestParam String userPw, @RequestParam String userName,
			@RequestParam String userEmail, @RequestParam String userPw2) {
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

	@PostMapping("/update")
	public String updateUser(@RequestParam String userId, @RequestParam String userPw, @RequestParam String userName,
			@RequestParam String userEmail, @RequestParam(required = false) String newPassword) {

		// 현재 비밀번호 검증
		User user = userService.findByUserId(userId).orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

		// 현재 비밀번호가 일치하는지 확인
		if (!passwordEncoder.matches(userPw, user.getUserPw())) {
			throw new IllegalArgumentException("현재 비밀번호가 일치하지 않습니다.");
		}

		// 새로운 비밀번호가 제공된 경우 비밀번호 업데이트
		if (newPassword != null && !newPassword.isEmpty()) {
			userService.updateUserPassword(userId, newPassword);
		}

		// 사용자 이름과 이메일만 업데이트
		userService.updateUser(userId, userName, userEmail);

		return "redirect:/mypage";
	}

	@GetMapping("/mypage/posts")
	public String getUserPosts(Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentUserId = authentication.getName(); // 현재 로그인한 사용자 ID를 가져옴

		List<Board> boards = boardService.getBoardsByUserId(currentUserId); // 사용자 ID로 게시글 조회
		model.addAttribute("boards", boards); // 모델에 게시글 추가

		return "pages/user/mypage_posts"; // 게시글 목록 페이지로 이동
	}

	@GetMapping("/mypage/likes")
	public String getAllLikes(Model model, @RequestParam(defaultValue = "1") int page) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentUserId = authentication.getName(); // 현재 로그인한 사용자 ID를 가져옴

		List<Long> likedRecipeIds = likeService.getLikedRecipeIdsByUserId(currentUserId);

		int ITEMS_PER_PAGE = 9;

		// 레시피 정보를 가져올 페이지 처리
		int totalPages = (int) Math.ceil((double) likedRecipeIds.size() / ITEMS_PER_PAGE);
		List<Recipe> recipes = recipeService.getRecipesByIds(likedRecipeIds.subList((page - 1) * ITEMS_PER_PAGE,
				Math.min(page * ITEMS_PER_PAGE, likedRecipeIds.size())));

		model.addAttribute("recipes", recipes);
		model.addAttribute("totalPages", totalPages);
		model.addAttribute("currentPage", page);

		return "pages/user/mypage_likes";
	}

}
