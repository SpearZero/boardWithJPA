package jpa.board.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import jpa.board.dto.Member;
import jpa.board.persistence.MemberRepository;

public class MemberServiceImpl implements MemberService{
	
	@Autowired
	private MemberRepository memberRepository;

	@Override
	public boolean checkExistMember(String username) {
		Optional<Member> memberOpt = memberRepository.findByUsername(username);
		
		return memberOpt.isPresent();
	}
}
