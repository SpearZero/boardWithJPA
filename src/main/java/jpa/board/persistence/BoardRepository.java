package jpa.board.persistence;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import jpa.board.dto.Board;

public interface BoardRepository extends CrudRepository<Board, Long>{

	List<Board> findAll(Pageable pageable);
	
}
