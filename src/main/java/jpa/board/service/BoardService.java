package jpa.board.service;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.data.domain.Pageable;
import org.springframework.ui.Model;

import com.fasterxml.jackson.annotation.JsonCreator.Mode;

import jpa.board.dto.BoardList;
import jpa.board.entity.Board;

public interface BoardService {
	
	public List<BoardList> getBoardList(int page);
	
	public List<String> getPagination(int page);
	
	public Optional<Board> getBoard(String getSeq) throws Exception;
	
	// public String setBoardContent(HttpServletRequest request, Model model);
	
	public void deleteBoardContent(HttpServletRequest request);
	
	public String getWriteBoard(HttpServletRequest request);
	
	public String postWriteBoard(HttpServletRequest request);
	
	public String setUpdateBoardContent(HttpServletRequest request, Model model);
	
	public String updateBoardContent(HttpServletRequest request);
}
