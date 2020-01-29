package jpa.board.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jpa.board.dto.Board;
import jpa.board.persistence.BoardRepository;
import jpa.board.service.BoardService;

@Controller
@RequestMapping("/board")
public class BoardController {
	
	@Autowired
	private BoardService boardService;
	
    @GetMapping("")
    public String goBoard(HttpServletRequest request, Model model) {
    	
    	try {
    		int offset = Integer.parseInt(request.getParameter("offset"));
    		int limit = Integer.parseInt(request.getParameter("limit"));
    		Pageable pageable = PageRequest.of(offset, limit, Sort.Direction.DESC, "seq");
    		
    		List<Board> boardList = boardService.getBoardList(pageable);
    		model.addAttribute("boardList", boardList);
    		
    	} catch (Exception e) {
    		e.printStackTrace();
		}
    	
    	return "/board/board";
    }
}
