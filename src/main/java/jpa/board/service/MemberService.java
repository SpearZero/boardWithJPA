package jpa.board.service;

import jpa.board.dto.Member;

public interface MemberService {
	boolean checkExistMember(String username);
}
