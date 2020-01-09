package jpa.board.service;

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
			e.printStackTrace();
			entity = new ResponseEntity<>("error", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return entity;
	}
}
