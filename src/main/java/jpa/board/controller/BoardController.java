package jpa.board.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import jpa.board.dto.BoardList;
import jpa.board.dto.Content;
import jpa.board.entity.Board;
import jpa.board.entity.Member;
import jpa.board.persistence.BoardRepository;
import jpa.board.service.BoardService;
import jpa.board.service.MemberService;
import jpa.board.util.ParameterUtils;

@Controller
@RequestMapping("/board")
public class BoardController {
	
	@Autowired
	private BoardService boardService;
	
	@Autowired
	private MemberService meberService;
	
	@Autowired
	private BoardRepository boardRepo;
	
    @GetMapping("")
    public String goBoard(HttpServletRequest request, Model model) {
    	
    	try {
    		String reqPage = ParameterUtils.getReqParameter(request, "page", "1");
    		int page = Integer.parseInt(reqPage);
    		
    		List<BoardList> boardList = boardService.getBoardList(page);
    		List<String> pageList = boardService.getPagination(page);
    		
    		model.addAttribute("boardList", boardList);
    		model.addAttribute("pageList", pageList);
    		model.addAttribute("page", page);
    	} catch (Exception e) {
    		e.printStackTrace();
		}
    	
    	return "/board/board";
    }
    
    @GetMapping("/boardContent")
    public String goBoardContent(HttpServletRequest request, Model model) {
    	String seq = ParameterUtils.getReqParameter(request, "seq", "-1");
    	String page = ParameterUtils.getReqParameter(request, "page", "1");
    	String goPage = "redirect:/board?page="+page;
    	
    	try {
    		Board board = boardService.getBoard(seq).orElse(new Board());
    		Member member = Optional.ofNullable((Member)request.getSession().getAttribute("member")).orElse(new Member());
    		
    		System.out.println(member.getId() + " : " + board.getMember().getId());
    		
    		model.addAttribute("writer", member.getId() == board.getMember().getId());
    		model.addAttribute("board", board);
    		model.addAttribute("page", page);
    		
    		goPage = "/board/boardContent";
    	} catch(Exception e) {
    		e.printStackTrace();
    	}
    	
    	return goPage;
    }
    
    @GetMapping("/delete/boardContent")
    public String deleteBoardContent(HttpServletRequest request, Model model) {
    	String seq = ParameterUtils.getReqParameter(request, "seq", "-1");
    	String page = ParameterUtils.getReqParameter(request, "page", "1");
    	String goPage = "redirect:/board?page="+page;
    	
    	try {
    		Board board = boardService.getBoard(seq).orElse(new Board());
    		
    		Member member = Optional.ofNullable((Member)request.getSession().getAttribute("member")).orElse(new Member());
    		boardService.deleteBoardContent(board, member);
    	} catch (Exception e) {
    		e.printStackTrace();
		}
    	
    	return goPage;
    }
    
    @GetMapping("/write")
    public String getWriteBoard(HttpServletRequest request, Model model) {
    	String page = ParameterUtils.getReqParameter(request, "page", "1");
    	String goPage = "/board?page="+page;
    	
    	try {
    		HttpSession session = request.getSession(false);
    		
    		if(session != null) {
    			Member member = Optional.ofNullable((Member)request.getSession().getAttribute("member")).orElse(new Member(-1L));
    			goPage = member.getId() > -1 ? "board/writeBoard" : goPage;
    		}
    	} catch (Exception e) {
    		e.printStackTrace();
		}
    	
    	model.addAttribute("page", page);
    	return goPage;
    }
    
    @PostMapping("/write/boardContent")
    public String writeBoardContent(HttpServletRequest request, @ModelAttribute Content content) {
    	String goPage = "redirect:/board";
    	
    	try {
    		HttpSession session = request.getSession(false);
    		
    		if(session == null) {
    			return goPage;
    		}
    		
			Member sessionMember = Optional.ofNullable((Member)request.getSession().getAttribute("member")).orElse(new Member());
			Member member = meberService.findMember(sessionMember.getId()).orElse(new Member());
			boardService.writeBoardContent(member, content);
    	} catch (Exception e) {
    		e.printStackTrace();
		}
    	
    	
    	return goPage;
    }
    
    @GetMapping("/update")
    public String getUpdateBoard(HttpServletRequest request, Model model) {
    	String page = ParameterUtils.getReqParameter(request, "page", "1");
    	String seq = ParameterUtils.getReqParameter(request, "seq", "-1");
    	String goPage = "redirect:/board?page="+page;
    	
    	try {
    		Board board = boardService.getBoard(seq).orElse(new Board());
    		
    		Member member = Optional.ofNullable((Member)request.getSession().getAttribute("member")).orElse(new Member());
    		boolean writer = member.getId() == board.getMember().getId();
    		
    		model.addAttribute("board", board);
    		model.addAttribute("writer", writer);
    		model.addAttribute("page", page);
    		
    		goPage = "/board/updateBoard";
    	} catch (Exception e) {
    		e.printStackTrace();
		}
    	
    	return goPage;
    }
    
    @PostMapping("/update/boardContent")
    public String postUpdateBoard(HttpServletRequest request, @ModelAttribute Content content) {
    	String page = ParameterUtils.getReqParameter(request, "page", "1");
    	String seq = ParameterUtils.getReqParameter(request, "seq", "-1");
    	String goPage = "redirect:/board?page="+page;
    	
    	try {
    		Board board = boardService.getBoard(seq).orElse(new Board());
    		Member member = Optional.ofNullable((Member)request.getSession().getAttribute("member")).orElse(new Member());
    		boolean writer = member.getId() == board.getMember().getId();
    		
    		if(writer) {
    			boardService.updateBoardContent(board, content);
    		}
    	} catch(Exception e) {
    		e.printStackTrace();
    	}
    	
    	return goPage;
    }
}
