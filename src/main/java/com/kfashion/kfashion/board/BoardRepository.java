package com.kfashion.kfashion.board;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Transactional(readOnly = true)
public interface BoardRepository extends JpaRepository<Board, Long> {
    List<Board> findAllByBoardName(String boardName);
}
