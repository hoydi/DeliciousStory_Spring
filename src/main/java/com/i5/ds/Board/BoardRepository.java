package com.i5.ds.Board;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardRepository extends JpaRepository<Board, Integer> {

    // 사용자 ID로 게시글 목록을 가져오는 메서드
    List<Board> findByUserId(String userId);

    // 대소문자 구분 없이 제목으로 검색
    @Query("SELECT b FROM Board b WHERE LOWER(b.title) LIKE LOWER(CONCAT('%', :title, '%'))")
    List<Board> searchByTitleIgnoringCase(@Param("title") String title);
    
 // 작성일자를 기준으로 역순 정렬하여 게시글 목록 가져오기 (페이징 처리 포함)
    Page<Board> findAllByOrderByPostDateDesc(org.springframework.data.domain.Pageable pageable);
    
    
}


