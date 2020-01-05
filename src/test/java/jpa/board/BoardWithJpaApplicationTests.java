package jpa.board;

import java.time.LocalDateTime;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import jpa.board.dto.Board;
import jpa.board.persistence.BoardRepository;


@SpringBootTest
class BoardWithJpaApplicationTests {
	
	@Autowired
	private BoardRepository boardRepo;
	
	@Test
	public void boardInsertTest() {
		for(int i = 0; i < 100; i++) {
			Board board = new Board();
			board.setTitle("제목"+i);
			board.setContent("내용"+i);
			board.setWriter("제목"+i);
			board.setCreatedDate(LocalDateTime.now());
			board.setModifiedDate(LocalDateTime.now());
			
			boardRepo.save(board);
		}
	}
}
