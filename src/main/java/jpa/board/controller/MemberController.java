package jpa.board.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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
import jpa.board.persistence.MemberRepository;
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
    public ResponseEntity<String> dupIdCheck(HttpServletRequest request) {
    	String userName = request.getParameter("username");
    	
    	return memberService.dupIdCheck(userName);
    }
    
    @GetMapping("/login")
    public String goLoginPage() {
    	return "/member/login";
    }
    
    @PostMapping("/login")
    public ResponseEntity<String> doLogin(HttpServletRequest request, @RequestBody Member member) {
    	ResponseEntity<String> entity = null;
    	HttpSession session = request.getSession();
    	
    	try {
    		Member findMember = memberService.findUserUsingNamePassword(member.getUsername(), member.getPassword()).orElse(new Member(-1L));
    		boolean isLoginResult = findMember.getId() > -1 ? true : false;
    		
    		if(isLoginResult) {
    			findMember.setPassword("");
    			session.setAttribute("member", findMember);
    		}
    		
    		session.setAttribute("isLogin", isLoginResult);
    		entity = new ResponseEntity<String>(String.valueOf(isLoginResult), HttpStatus.OK);
    	} catch (Exception e) {
    		entity = new ResponseEntity<String>("error", HttpStatus.INTERNAL_SERVER_ERROR);
    		e.printStackTrace();
		}
    	
    	return entity;
	}
    
    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
    	try {
    		HttpSession session = request.getSession(false);
    		
    		if(session != null) {
    			session.invalidate();
    		}
    	} catch (Exception e) {
    		e.printStackTrace();
		}
    	
    	return "redirect:/";
    }
    
    @GetMapping("/beforeMyInfo")
    public String goMyInfo() {
    	
    	return "/member/beforeMyInfo";
    }
    
    @PostMapping("/beforeMyInfo")
    public String checkMyInfo(HttpServletRequest request, @RequestParam String password) {
    	String goPage = "redirect:/";
    	
    	try {
    		HttpSession session = request.getSession(false);
    		
    		if(session == null) {
    			return goPage;
    		}
    		
    		Member sessionMember = (Member)session.getAttribute("member");
    		Member member = memberService.findMember(sessionMember.getId()).orElse(new Member());
    		
    		Boolean checkResult = password.equals(member.getPassword());
    		
    		goPage = checkResult ? "redirect:/member/myInfo" : "/member/beforeMyInfo";
    	} catch (Exception e) {
    		e.printStackTrace();
		}
    	
    	return goPage;
    }
    
    @GetMapping("/myInfo")
    public String myInfo() {
    	
    	return "/member/myInfo";
    }
    
    @PostMapping("/myInfo")
    public ResponseEntity<String> modifyMyInfo(HttpServletRequest request, @RequestBody Member member) {
    	ResponseEntity<String> entity = null;
    	String result = "fail";
    	
    	try { 
    		HttpSession session = request.getSession(false);
    		
    		if(session == null) {
    			return new ResponseEntity<String>(result, HttpStatus.OK);
    		}
    		
    		Member sessionMember = (Member)session.getAttribute("member");
    		
    		Member updateMember = memberService.findMember(sessionMember.getId()).orElse(new Member(-1L));
    		if(updateMember.getId() > -1) {
    			return new ResponseEntity<String>(result, HttpStatus.OK);
    		}
    		
    		String userName = member.getUsername();
    		Boolean checkResult = userName.equals(sessionMember.getUsername());
    		if(checkResult) {
    			memberService.updateMember(updateMember, member);
    		}
    		
    		result = "success";
    		entity = new ResponseEntity<String>(result, HttpStatus.OK);
    	} catch (Exception e) {
    		e.printStackTrace();
    		entity = new ResponseEntity<String>("error", HttpStatus.INTERNAL_SERVER_ERROR);
		}
    	
    	return entity;
    }
}
