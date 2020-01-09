package jpa.board.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jpa.board.service.MemberService;

@Controller
@RequestMapping("/member")
public class MemberController {
	
	@Autowired
	private MemberService memberService;
	
    @GetMapping("/signUp")
    public String signUp() {
    	return "/member/signUp";
    }
    
    @GetMapping("/dupCheckResult")
    public ResponseEntity<String> dupIdCheck(HttpServletRequest request) {
    	return memberService.dupIdCheck(request);
    }
}
