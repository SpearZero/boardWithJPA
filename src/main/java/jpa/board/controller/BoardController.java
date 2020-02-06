package jpa.board.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jpa.board.dto.BoardList;
import jpa.board.entity.Board;
import jpa.board.entity.Member;
import jpa.board.persistence.BoardRepository;
import jpa.board.service.BoardService;

@Controller
@RequestMapping("/board")
public class BoardController {
	
	@Autowired
	private BoardService boardService;
	
	@Autowired
	private BoardRepository boardRepo;
	
    @GetMapping("")
    public String goBoard(HttpServletRequest request, Model model) {
    	
    	try {
    		int page = 1;
    		String requestPage = request.getParameter("page");
    		
    		if(!(requestPage == null || StringUtils.isEmpty(requestPage.trim()))) {
    			page = Integer.parseInt(requestPage);
    		}
    		
    		List<BoardList> boardList = boardService.getBoardList(page);
    		List<String> pageList = boardService.getPagination(page);
    		
    		model.addAttribute("boardList", boardList);
    		model.addAttribute("pageList", pageList);
    	} catch (Exception e) {
    		e.printStackTrace();
		}
    	
    	return "/board/board";
    }
    
    @GetMapping("/boardContent")
    public String goBoardContent(HttpServletRequest request, Model model) {
    	String getSeq = request.getParameter("seq");
    	Long seq = 0L;
    	
		if(!(getSeq == null || StringUtils.isEmpty(getSeq.trim()))) {
			seq = Long.parseLong(getSeq);
		}
		
		Optional<Board> boardOpt = boardRepo.findById(seq);
		
		if(boardOpt.isPresent()) {
			Board board = boardOpt.get();
			model.addAttribute("board", board);
			
			Member member = (Member)request.getSession().getAttribute("member");
			if(member != null) {
				boolean writer = member.getId() == board.getMember().getId() ? true : false;
				model.addAttribute("writer", writer);
			}
		}
		
    	return "board/boardContent";
    }
}
