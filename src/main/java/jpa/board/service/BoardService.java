package jpa.board.service;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.data.domain.Pageable;
import org.springframework.ui.Model;

import com.fasterxml.jackson.annotation.JsonCreator.Mode;

import jpa.board.dto.BoardList;
import jpa.board.dto.Content;
import jpa.board.entity.Board;
import jpa.board.entity.Member;

public interface BoardService {
	
	public List<BoardList> getBoardList(int page);
	
	public List<String> getPagination(int page);
	
	public Optional<Board> getBoard(String getSeq) throws Exception;
	
	public void deleteBoardContent(Board board, Member member);
	
	public void writeBoardContent(Member member, Content content);
	
	public void updateBoardContent(Board board, Content content);
}
