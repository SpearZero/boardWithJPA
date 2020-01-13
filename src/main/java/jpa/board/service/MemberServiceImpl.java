package jpa.board.service;

import java.time.LocalDateTime;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import jpa.board.dto.Member;
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
	public ResponseEntity<String> dupIdCheck(HttpServletRequest request) {
		String username = request.getParameter("username");
		
		ResponseEntity<String> entity = null;
		try {
			String checkResult = String.valueOf(checkExistMember(username));
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
			LocalDateTime date = LocalDateTime.now();
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
}
