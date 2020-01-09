package jpa.board.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import jpa.board.dto.Member;

public interface MemberService {
	boolean checkExistMember(String username);
	
	ResponseEntity<String> dupIdCheck(HttpServletRequest request);
}
