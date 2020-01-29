package jpa.board.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import jpa.board.dto.Board;
import jpa.board.persistence.BoardRepository;

@Service
public class BoardServiceImpl implements BoardService {
	
	@Autowired
	BoardRepository boardRepo;

	@Override
	public List<Board> getBoardList(Pageable pageable) {
		List<Board> boardList = boardRepo.findAll(pageable);
		return boardList;
	}
}
