package jpa.board;

import java.time.LocalDateTime;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import jpa.board.dto.Board;
import jpa.board.dto.Member;
import jpa.board.persistence.BoardRepository;
import jpa.board.persistence.MemberRepository;


@SpringBootTest
class BoardWithJpaApplicationTests {
	
	@Autowired
	private BoardRepository boardRepo;
	
	@Autowired
	private MemberRepository memberRepo;
	
//	@Test
//	public void boardInsertTest() {
//		for(int i = 0; i < 100; i++) {
//			Board board = new Board();
//			board.setTitle("제목"+i);
//			board.setContent("내용"+i);
//			board.setWriter("제목"+i);
//			board.setCreatedDate(LocalDateTime.now());
//			board.setModifiedDate(LocalDateTime.now());
//			
//			boardRepo.save(board);
//		}
//	}
	
//	@Test
//	public void memberBoardInsert() {
//		Member member = new Member();
//		member.setUsername("abcd123");
//		member.setPassword("pass123");
//		member.setCreatedDate(LocalDateTime.now());
//		member.setModifiedDate(LocalDateTime.now());
//		memberRepo.save(member);
//		
//		Board board1 = new Board();
//		board1.setTitle("제목1");
//		board1.setContent("내용1");
//		board1.setCreatedDate(LocalDateTime.now());
//		board1.setModifiedDate(LocalDateTime.now());
//		board1.setWriter(member.getUsername());
//		board1.setMember(member);
//		boardRepo.save(board1);
//		
//		Board board2 = new Board();
//		board2.setTitle("제목2");
//		board2.setContent("내용2");
//		board2.setCreatedDate(LocalDateTime.now());
//		board2.setModifiedDate(LocalDateTime.now());
//		board2.setWriter(member.getUsername());
//		board2.setMember(member);
//		boardRepo.save(board2);
//		
//	}
	
	@Test
	public void existMemberCheck() {
		Optional<Member> memberOpt = memberRepo.findByUsername("12345");
		
		System.out.println("== Exist Member Check ==");
		System.out.println(memberOpt.isPresent());
	}
}
