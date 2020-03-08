package jpa.board.service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.hibernate.service.spi.OptionallyManageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import jpa.board.entity.Board;
import jpa.board.entity.Member;
import jpa.board.persistence.BoardRepository;
import jpa.board.persistence.MemberRepository;

@Service
public class MemberServiceImpl implements MemberService{
	
	@Autowired
	private MemberRepository memberRepository;

	@Override
	public boolean checkExistMember(String username) {
		Optional<Member> memberOpt = memberRepository.findByUsername(username);
		
		return memberOpt.isPresent();
	}

	@Override
	public ResponseEntity<String> dupIdCheck(String userName) {
		ResponseEntity<String> entity = null;
		try {
			String checkResult = String.valueOf(checkExistMember(userName));
			entity = new ResponseEntity<>(checkResult, HttpStatus.OK);
		} catch (Exception e) {
			entity = new ResponseEntity<>("error", HttpStatus.INTERNAL_SERVER_ERROR);
			e.printStackTrace();
		}
		
		return entity;
	}

	@Override
	public ResponseEntity<String> singUp(Member member) {
		ResponseEntity<String> entity = null;
		
		try {
			String date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
			member.setCreatedDate(date);
			member.setModifiedDate(date);
			
			Member savedMember = memberRepository.save(member);
			
			String result = savedMember != null ? "true" : "false";
			entity = new ResponseEntity<String>(result, HttpStatus.OK);
		} catch (Exception e) {
			entity = new ResponseEntity<String>("error", HttpStatus.INTERNAL_SERVER_ERROR);
			e.printStackTrace();
		}
		
		return entity;
	}

	@Override
	public Optional<Member> findUserUsingNamePassword(String username, String password) {
		return memberRepository.findByUsernameAndPassword(username, password);
	}
	
	public void updateMember(Member updateMember, Member member) {
		String date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
		updateMember.setModifiedDate(date);
		updateMember.setPassword(member.getPassword());
		memberRepository.save(updateMember);
	}

	@Override
	public Optional<Member> findMember(Long id) {
		return memberRepository.findById(id);
	}
	
}
