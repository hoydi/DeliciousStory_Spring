package com.i5.ds.Board;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BoardService {

    @Autowired
    private BoardRepository boardRepository;

    // 모든 게시글을 페이지네이션하여 조회
    public Page<Board> getBoardsPaged(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return boardRepository.findAllByOrderByPostDateDesc(pageable);
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
    
 // 사용자 ID로 게시글 목록을 가져오는 메서드
    public List<Board> getBoardsByUserId(String userId) {
        return boardRepository.findByUserId(userId);
    }

	public Page<Board> findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
