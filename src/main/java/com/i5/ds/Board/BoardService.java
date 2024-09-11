package com.i5.ds.Board;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BoardService {

	@Autowired
	private BoardRepository boardRepository;

	public List<Board> getAllBoards() {
		return boardRepository.findAll();
	}

	public Optional<Board> getBoardById(Integer id) {
		return boardRepository.findById(id);
	}

	public Board saveBoard(Board board) {
		return boardRepository.save(board);
	}

	public void deleteBoard(Integer id) {
		boardRepository.deleteById(id);
	}

	public List<Board> getBoardsByUserId(String userId) {
		return boardRepository.findByUserId(userId);
	}
    // 모든 게시글을 조회합니다.
    public List<Board> getAllBoards() {
        return boardRepository.findAll();
    }

    // 특정 ID의 게시글을 조회합니다.
    public Optional<Board> getBoardById(Integer id) {
        return boardRepository.findById(id);
    }

    // 게시글을 저장합니다. userId가 null이면 예외를 발생시킵니다.
    public Board saveBoard(Board board) {
        if (board.getUserId() == null) {
            throw new IllegalArgumentException("UserId는 null일 수 없습니다.");
        }
        return boardRepository.save(board);
    }

    // 특정 ID의 게시글을 삭제합니다.
    public void deleteBoard(Integer id) {
        boardRepository.deleteById(id);
    }
 // 대소문자 구분 없이 제목으로 게시글 검색
    public List<Board> searchBoardsByTitle(String title) {
        return boardRepository.searchByTitleIgnoringCase(title);
    }
}
