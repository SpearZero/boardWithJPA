package jpa.board.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import jpa.board.entity.Member;
import jpa.board.service.MemberService;

@Controller
@RequestMapping("/member")
public class MemberController {
	
	@Autowired
	private MemberService memberService;
	
    @GetMapping("/signUp")
    public String getSignUp() {
    	return "/member/signUp";
    }
    
    @PostMapping("/signUp")
    public ResponseEntity<String> postSignUp(@RequestBody Member member) {
    	return memberService.singUp(member);
    }
    
    @GetMapping("/dupCheckResult")
    public ResponseEntity<String> dupIdCheck() {
    	return memberService.dupIdCheck();
    }
    
    @GetMapping("/login")
    public String goLoginPage() {
    	return "/member/login";
    }
    
    @PostMapping("/login")
    public ResponseEntity<String> doLogin(@RequestBody Member member) {
    	
    	return memberService.doLogin(member);
	}
    
    @GetMapping("/logout")
    public String logout() {
    	memberService.doLogout();
    	
    	return "redirect:/";
    }
    
    @GetMapping("/beforeMyInfo")
    public String goMyInfo() {
    	
    	return "/member/beforeMyInfo";
    }
    
    @PostMapping("/beforeMyInfo")
    public String checkMyInfo(@RequestParam String password) {
    	return memberService.checkMyInfo(password);
    }
    
    @GetMapping("/myInfo")
    public String myInfo() {
    	
    	return "/member/myInfo";
    }
    
    @PostMapping("/myInfo")
    public ResponseEntity<String> postMyInfo(@RequestBody Member member) {
    	return memberService.changeMyInfo(member);
    }
}
