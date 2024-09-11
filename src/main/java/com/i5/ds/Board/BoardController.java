	package com.i5.ds.Board;
	
	import org.springframework.beans.factory.annotation.Autowired;
	import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
	import org.springframework.ui.Model;
	import org.springframework.web.bind.annotation.*;

import com.i5.ds.User.User;
import com.i5.ds.User.UserService;

import java.util.Date;
import java.util.List;
	import java.util.Optional;
	
	@Controller
	@RequestMapping("/boards") // "/boards" 경로로 들어오는 요청을 이 컨트롤러에서 처리
	public class BoardController {
		@Autowired
		private UserService userService;

	    @Autowired
	    private BoardService boardService; // BoardService를 주입 받아 사용
	
	    // 전체 게시글 목록을 가져와서 뷰에 전달
	    @GetMapping
	    public String getAllBoards(Model model) {
	        List<Board> boards = boardService.getAllBoards(); // 모든 게시글을 가져옴
	        model.addAttribute("boards", boards); // 모델에 게시글 목록을 추가
	        return "pages/board/list"; // 게시글 목록을 보여주는 뷰를 반환
	    }
	
	    // 특정 ID의 게시글을 가져옴
	    @GetMapping("/{id}")
	    public String getBoardById(@PathVariable Integer id, Model model) {
	        Optional<Board> board = boardService.getBoardById(id); // ID로 게시글 검색
	        if (board.isPresent()) {
	            model.addAttribute("board", board.get()); // 모델에 게시글 추가
	            return "pages/board/detail"; // 게시글 상세 페이지 뷰 반환
	        } else {
	            return "redirect:/boards"; // 게시글이 없으면 목록 페이지로 리다이렉트
	        }
	    }
	
	    // 특정 ID의 게시글을 수정
	    @PutMapping("/{id}")
	    public ResponseEntity<Board> updateBoard(@PathVariable Integer id, @RequestBody Board board) {
	        // 수정할 게시글이 존재하는지 확인
	        if (!boardService.getBoardById(id).isPresent()) {
	            return ResponseEntity.notFound().build(); // 존재하지 않으면 404 Not Found 반환
	        }
	        board.setPostId(id); // 기존 게시글의 ID를 설정
	        return ResponseEntity.ok(boardService.saveBoard(board)); // 수정된 게시글을 저장하고 반환
	    }
	
	    // 특정 ID의 게시글을 삭제
	    @DeleteMapping("/{id}")
	    public ResponseEntity<Void> deleteBoard(@PathVariable Integer id) {
	        // 삭제할 게시글이 존재하는지 확인
	        if (!boardService.getBoardById(id).isPresent()) {
	            return ResponseEntity.notFound().build(); // 존재하지 않으면 404 Not Found 반환
	        }
	        boardService.deleteBoard(id); // 게시글 삭제
	        return ResponseEntity.noContent().build(); // 204 No Content 반환
	    }
	
	    // 글쓰기 페이지로 이동하는 메서드
	    @GetMapping("/post")
	    public String showWritePage(Model model) {
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
	    	
	    	
	        // 새 글 작성을 위한 빈 Board 객체를 모델에 추가
	        model.addAttribute("board", new Board());
	        return "pages/board/post"; // 글쓰기 페이지 경로 (HTML 파일명)
	    }
	
	    // 게시글 추가
	    @PostMapping("/post")
	    public String postBoard(@RequestParam String userId,
	                            @RequestParam String title,
	                            @RequestParam String content) {
	        Date today = new Date(); // 현재 날짜와 시간
	        Board newBoard = new Board(userId, title, content, today); // userId와 오늘 날짜를 설정
	        boardService.saveBoard(newBoard); // 게시글 저장
	        return "redirect:/boards"; // 게시글 목록 페이지로 리다이렉트
	    }
	    
	 // 제목으로 게시글 검색
	    @GetMapping("/search")
	    public String searchBoards(@RequestParam("query") String query, Model model) {
	        List<Board> boards = boardService.searchBoardsByTitle(query);
	        model.addAttribute("boards", boards);
	        return "pages/board/list"; // 게시글 목록을 보여주는 뷰를 반환
	    }
	    

	}
