package com.i5.ds.Board;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardRepository extends JpaRepository<Board, Integer> {
	// Custom query methods can be defined here if needed
	// 사용자 ID로 게시글 목록을 가져오는 메서드
	List<Board> findByUserId(String userId);
}
