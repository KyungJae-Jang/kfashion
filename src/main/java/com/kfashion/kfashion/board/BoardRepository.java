package com.kfashion.kfashion.board;

import com.kfashion.kfashion.account.Account;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface BoardRepository extends JpaRepository<Board, Long> {

    Page<Board> findBoardByBoardName(String boardName, Pageable pageable);

    @Query(
            value = "SELECT board FROM Board board WHERE board.subject LIKE %:keyword%",
            countQuery = "SELECT COUNT(board.id) FROM Board board WHERE board.subject LIKE %:keyword"
    )
    Page<Board> findBoardByKeyword(String keyword, Pageable pageable);

    @Query(
            value = "SELECT board FROM Board board INNER JOIN board.owner account"
    )
    Page<Board> findBoardByAccount(Long id, Pageable pageable);
}
