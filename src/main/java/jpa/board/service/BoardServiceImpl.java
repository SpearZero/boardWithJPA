package jpa.board.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import jpa.board.dto.BoardList;
import jpa.board.entity.Board;
import jpa.board.entity.Member;
import jpa.board.persistence.BoardRepository;
import jpa.board.persistence.MemberRepository;

@Service
public class BoardServiceImpl implements BoardService {
	
	@Autowired
	BoardRepository boardRepo;
	
	@Autowired
	MemberRepository memberRepo;
	
	@Value("${board.countList}")
	private int countList;
	
	@Value("${board.countPage}")
	private int countPage;

	@Override
	public List<BoardList> getBoardList(int page) {
		Pageable pageable = PageRequest.of((page-1), countList, Sort.Direction.DESC, "seq");
		List<BoardList> boardList = boardRepo.findBoardList(pageable);
		return boardList;
	}

	@Override
	public List<String> getPagination(int page) {
		ArrayList<String> pageList = new ArrayList<>();
		int totalCount = ((int)boardRepo.count());
		
		int totalPage = totalCount / countList;
		
		if(totalCount % countList > 0) {
			totalPage++;
		}
		
		int startPage = ((page - 1) / countPage) * countPage + 1;
		int endPage = startPage + countPage - 1;
		
		if(endPage > totalPage) {
			endPage = totalPage;
		}
		
		if(startPage > 1) {
			String firstPage = "<li class=\"page-item\"><a class=\"page-link\" href=\"/board?page=1\">첫페이지</a></li>";
			pageList.add(firstPage);
		}
		
		if(page > 1) {
			String previousPage = "<li class=\"page-item\"><a class=\"page-link\" href=\"/board?page=" + (page-1) + "\">이전페이지</a></li>";
			pageList.add(previousPage);
		}
		
		for(int i = startPage; i <= endPage; i++) {
			String pageIndex = "";
			
			if(i == page) {
				pageIndex = "<li class=\"page-item active\"><a class=\"page-link\" href=\"/board?page=" + i + "\">" + i + "</a></li>";
				pageList.add(pageIndex);
			} else {
				pageIndex = "<li class=\"page-item\"><a class=\"page-link\" href=\"/board?page=" + i + "\">" + i + "</a></li>";
				pageList.add(pageIndex);
			}
		}
		
		if(page < totalPage) {
			String nextPage = "<li class=\"page-item\"><a class=\"page-link\" href=\"/board?page=" + (page+1) + "\">다음페이지</a></li>";
			pageList.add(nextPage);
		}
		
		if(endPage < totalPage) {
			String lastPage = "<li class=\"page-item\"><a class=\"page-link\" href=\"/board?page=" + totalPage + "\">마지막페이지</a></li>";
			pageList.add(lastPage);
		}
		
		return pageList;
	}

	@Override
	public void setBoardContent(HttpServletRequest request, Model model, Long seq) {
		try {
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
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void deleteBoardContent(HttpServletRequest request, Long seq) {
		try {
			Optional<Board> boardOpt = boardRepo.findById(seq);
			
			if(boardOpt.isPresent()) {
				Board board = boardOpt.get();
				
				Member member = (Member)request.getSession().getAttribute("member");
				if(member != null) {
					boolean writer = member.getId() == board.getMember().getId() ? true : false;
					
					if(writer) {
						boardRepo.deleteById(seq);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public String getWriteBoard(HttpServletRequest request) {
		String goPage = "/board?page=1";
		try {
			HttpSession session = request.getSession(false);
			
			if(session != null) {
				Member sessionMember = (Member)request.getSession().getAttribute("member");
				goPage = sessionMember != null ? "board/writeBoard" : goPage;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return goPage;
	}

	@Override
	public String postWriteBoard(HttpServletRequest request) {
		String goPage = "redirect:/board";
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		
		try {
			HttpSession session = request.getSession(false);
			
			if(session != null) {
				Member sessionMember = (Member)request.getSession().getAttribute("member");
				
				if(sessionMember != null) {
					Optional<Member> optMember = memberRepo.findById(sessionMember.getId());
					
					if(optMember.isPresent()) {
						Member member = optMember.get();
						
						Board board = new Board();
						board.setTitle(title);
						board.setContent(content);
						
						String date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
						board.setCreatedDate(date);
						board.setModifiedDate(date);
						board.setMember(member);
						
						boardRepo.save(board);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return goPage;
	}
}
