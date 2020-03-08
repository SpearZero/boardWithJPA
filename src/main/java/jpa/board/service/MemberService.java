package jpa.board.service;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import jpa.board.entity.Board;
import jpa.board.entity.Member;

public interface MemberService {
	public boolean checkExistMember(String username);
	
	public ResponseEntity<String> dupIdCheck(String userName);
	
	public ResponseEntity<String> singUp(Member member);
	
	public Optional<Member> findUserUsingNamePassword(String username, String password);
	
	public Optional<Member> findMember(Long id);
	
	public void updateMember(Member updateMember, Member member);
}
