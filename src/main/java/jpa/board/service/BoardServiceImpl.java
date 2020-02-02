package jpa.board.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import jpa.board.dto.Board;
import jpa.board.persistence.BoardRepository;

@Service
public class BoardServiceImpl implements BoardService {
	
	@Autowired
	BoardRepository boardRepo;
	
	@Value("${board.countList}")
	private int countList;
	
	@Value("${board.countPage}")
	private int countPage;

	@Override
	public List<Board> getBoardList(int page) {
		Pageable pageable = PageRequest.of((page-1), countList, Sort.Direction.DESC, "seq");
		List<Board> boardList = boardRepo.findAll(pageable);
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
}
