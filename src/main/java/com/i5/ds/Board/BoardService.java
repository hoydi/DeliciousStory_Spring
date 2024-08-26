package com.i5.ds.Board;


import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class BoardService {
    private final BoardRepository boardRepository;

    public BoardService(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    public List<Board> getAllBoards() {
        return boardRepository.findAll();
    }

    public Board getBoardById(Long id) {
        return boardRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid board Id: " + id));
    }
}
