package com.i5.ds.Board;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardRepository extends JpaRepository<Board, Integer> {
    // Custom query methods can be defined here if needed
	 // 대소문자 구분 없이 제목으로 검색
    @Query("SELECT b FROM Board b WHERE LOWER(b.title) LIKE LOWER(CONCAT('%', :title, '%'))")
    List<Board> searchByTitleIgnoringCase(@Param("title") String title);
}
