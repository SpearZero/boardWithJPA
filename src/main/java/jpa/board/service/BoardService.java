package jpa.board.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import jpa.board.dto.BoardList;
import jpa.board.entity.Board;

public interface BoardService {
	
	List<BoardList> getBoardList(int page);
	
	List<String> getPagination(int page);
}
