package jpa.board.persistence;

import org.springframework.data.repository.CrudRepository;

import jpa.board.dto.Member;

public interface MemberRepository extends CrudRepository<Member, Long>{

}
