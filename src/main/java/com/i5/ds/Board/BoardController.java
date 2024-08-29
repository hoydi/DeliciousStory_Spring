package com.i5.ds.Board;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller // 이 클래스가 Spring MVC의 컨트롤러임을 나타냄
@RequestMapping("/boards") // "/boards" 경로로 들어오는 요청을 이 컨트롤러에서 처리
public class BoardController {

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
	public ResponseEntity<Board> getBoardById(@PathVariable Integer id) {
		Optional<Board> board = boardService.getBoardById(id); // ID로 게시글 검색
		// 게시글이 존재하면 200 OK와 함께 반환, 없으면 404 Not Found 반환
		return board.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
	}

	// 새로운 게시글을 생성
	@PostMapping
	public Board createBoard(@RequestBody Board board) {
		return boardService.saveBoard(board); // 요청 본문의 게시글 데이터를 저장
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
		// 새 글 작성을 위한 빈 Board 객체를 모델에 추가
		model.addAttribute("board", new Board());
		return "pages/board/board_write"; // 글쓰기 페이지 경로 (HTML 파일명)
	}

}
