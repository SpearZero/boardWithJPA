package jpa.board.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import jpa.board.dto.Board;

public interface BoardService {
	List<Board> getBoardList(Pageable pageable);
}
