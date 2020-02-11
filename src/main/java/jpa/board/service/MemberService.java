package jpa.board.service;

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
	
	public ResponseEntity<String> doLogin(HttpSession session, Member member);
	
	public void doLogout(HttpServletRequest request);
	
	public String checkMyInfo(HttpServletRequest request, String password);
	
	public ResponseEntity<String> changeMyInfo(HttpServletRequest request, Member member);
}
