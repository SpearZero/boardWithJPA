package jpa.board.persistence;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import jpa.board.dto.BoardList;
import jpa.board.entity.Board;

public interface BoardRepository extends CrudRepository<Board, Long>{

	List<Board> findAll(Pageable pageable);
	
	@Query("SELECT new jpa.board.dto.BoardList(b.seq,  m.username, b.title, b.createdDate, b.modifiedDate) FROM Board b INNER JOIN Member m ON m.id = b.member.id")
	List<BoardList> findBoardList(Pageable pageable);
}
