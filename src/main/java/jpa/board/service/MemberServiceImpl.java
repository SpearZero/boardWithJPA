package jpa.board.service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.hibernate.service.spi.OptionallyManageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import jpa.board.entity.Board;
import jpa.board.entity.Member;
import jpa.board.persistence.BoardRepository;
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
	public ResponseEntity<String> dupIdCheck(String userName) {
		ResponseEntity<String> entity = null;
		try {
			String checkResult = String.valueOf(checkExistMember(userName));
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
			String date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
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

	@Override
	public ResponseEntity<String> doLogin(HttpSession session, Member member) {
		ResponseEntity<String> entity = null;
		Optional<Member> memberOpt = memberRepository.findByUsernameAndPassword(member.getUsername(), member.getPassword());
		
		try {
			boolean isLoginResult = memberOpt.isPresent();
			
			if(isLoginResult) {
				Member sessionMember = memberOpt.get();
				sessionMember.setPassword("");
				session.setAttribute("member", sessionMember);
			}
			
			session.setAttribute("isLogin", isLoginResult);
			entity = new ResponseEntity<String>(String.valueOf(isLoginResult), HttpStatus.OK);
		} catch(Exception e) {
			entity = new ResponseEntity<String>("error", HttpStatus.INTERNAL_SERVER_ERROR);
			e.printStackTrace();
		}
		
		return entity;
	}

	@Override
	public void doLogout(HttpServletRequest request) {
		try {
			HttpSession session = request.getSession(false);
			
			if(session != null) {
				session.invalidate();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public String checkMyInfo(HttpServletRequest request, String password) {
		String goPage = "/";
		
		try {
			HttpSession session = request.getSession(false);
			
			// 세션이 없는 경우에 대한 코드 추가(나중에는 공통적으로 관리)
			if(session != null) {
				// 세션에 "member"가 없는 상황도 고려해야 함
				Member sessionMember = (Member)session.getAttribute("member");
				
				Optional<Member> optMember = memberRepository.findById(sessionMember.getId());
				
				Boolean checkResult = password.equals(optMember.get().getPassword());
				
				goPage = checkResult ? "redirect:/member/myInfo" : "/member/beforeMyInfo";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return goPage;
	}

	@Override
	public ResponseEntity<String> changeMyInfo(HttpServletRequest request, Member member) {
		ResponseEntity<String> entity = null;
		String result = "fail";
		
		try {
			HttpSession session = request.getSession(false);
			
			if(session != null) {
				Member sessionMember = (Member)session.getAttribute("member");
				String userName = member.getUsername();
				
				Boolean checkResult = userName.equals(sessionMember.getUsername());
				
				if(checkResult) {
					String date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
					Member updateMember = memberRepository.findById(sessionMember.getId()).get();
					updateMember.setModifiedDate(date);
					updateMember.setPassword(member.getPassword());
					memberRepository.save(updateMember);
					
					result = "success";
				}
				
			}
			
			entity = new ResponseEntity<String>(result, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			entity = new ResponseEntity<String>("error", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return entity;
	}
}
