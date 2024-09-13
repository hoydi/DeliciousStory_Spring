package com.i5.ds.Board;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
@RequestMapping("/boards")
public class BoardController {

    @Autowired
    private UserService userService;

    @Autowired
    private BoardService boardService;

    // 전체 게시글 목록을 가져와서 뷰에 전달
    @GetMapping
    public String getAllBoards(@RequestParam(defaultValue = "0") int page, Model model) {
        int pageSize = 10; // 한 페이지당 10개의 게시글
        Page<Board> boardPage = boardService.getBoardsPaged(page, pageSize);

        model.addAttribute("boards", boardPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", boardPage.getTotalPages());

        return "pages/board/list";
    }

    // 특정 ID의 게시글을 가져옴
    @GetMapping("/{id}")
    public String getBoardById(@PathVariable Integer id, Model model) {
        Optional<Board> board = boardService.getBoardById(id);
        if (board.isPresent()) {
            model.addAttribute("board", board.get());
            return "pages/board/detail";
        } else {
            return "redirect:/boards";
        }
    }

    // 특정 ID의 게시글을 수정
    @PutMapping("/{id}")
    public ResponseEntity<Board> updateBoard(@PathVariable Integer id, @RequestBody Board board) {
        if (!boardService.getBoardById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        board.setPostId(id);
        return ResponseEntity.ok(boardService.saveBoard(board));
    }

    @DeleteMapping("/{id}/delete")
    public String deleteBoard(@PathVariable("id") Integer id) {
        if (!boardService.getBoardById(id).isPresent()) {
            return "redirect:/boards"; // 게시글이 존재하지 않을 경우 목록으로 리다이렉트
        }
        boardService.deleteBoard(id);
        return "redirect:/boards"; // 삭제 후 게시글 목록으로 리다이렉트
    }

    // 글쓰기 페이지로 이동하는 메서드
    @GetMapping("/post")
    public String showWritePage(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserId = authentication.getName();

        Optional<User> userOpt = userService.findByUserId(currentUserId);
        if (userOpt.isPresent()) {
            model.addAttribute("user", userOpt.get());
        } else {
            model.addAttribute("error", "User not found.");
        }

        model.addAttribute("board", new Board());
        return "pages/board/post";
    }

    // 게시글 추가
    @PostMapping("/post")
    public String postBoard(@RequestParam String userId,
                            @RequestParam String title,
                            @RequestParam String content) {
        Date today = new Date();
        Board newBoard = new Board(userId, title, content, today);
        boardService.saveBoard(newBoard);
        return "redirect:/boards";
    }

    // 제목으로 게시글 검색
    @GetMapping("/search")
    public String searchBoards(@RequestParam("query") String query, Model model) {
        List<Board> boards = boardService.searchBoardsByTitle(query);
        model.addAttribute("boards", boards);
        return "pages/board/list";
    }
    
 

}
