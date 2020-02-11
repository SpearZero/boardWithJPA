package jpa.board.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.data.domain.Pageable;
import org.springframework.ui.Model;

import jpa.board.dto.BoardList;
import jpa.board.entity.Board;

public interface BoardService {
	
	public List<BoardList> getBoardList(int page);
	
	public List<String> getPagination(int page);
	
	public void setBoardContent(HttpServletRequest request, Model model, Long seq);
	
	public void deleteBoardContent(HttpServletRequest request, Long seq);
}
