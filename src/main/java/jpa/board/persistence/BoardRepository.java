package jpa.board.persistence;

import org.springframework.data.repository.CrudRepository;

import jpa.board.dto.Board;

public interface BoardRepository extends CrudRepository<Board, Long>{
	
}
