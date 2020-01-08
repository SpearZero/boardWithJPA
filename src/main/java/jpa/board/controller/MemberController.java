package jpa.board.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MemberController {
	
    @GetMapping("/signUp")
    public String signUp() {
    	return "/member/signUp";
    }
}
