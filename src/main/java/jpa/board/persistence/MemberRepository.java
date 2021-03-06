package jpa.board.persistence;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import jpa.board.entity.Member;

public interface MemberRepository extends CrudRepository<Member, Long>{
	
	Optional<Member> findByUsername(String username);
	
	Optional<Member> findByUsernameAndPassword(String username, String password);
}
