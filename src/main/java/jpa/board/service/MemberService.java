package jpa.board.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import jpa.board.dto.Member;

public interface MemberService {
	public boolean checkExistMember(String username);
	
	public ResponseEntity<String> dupIdCheck();
	
	public ResponseEntity<String> singUp(Member member);
	
	public ResponseEntity<String> doLogin(Member member);
	
	public void doLogout();
	
	public String checkMyInfo(String password);
	
	public ResponseEntity<String> changeMyInfo(Member member);
}
