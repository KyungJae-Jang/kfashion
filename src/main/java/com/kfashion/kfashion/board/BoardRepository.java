package com.kfashion.kfashion.board;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
public interface BoardRepository extends JpaRepository<Board, Long> {

    Page<Board> findBoardByBoardName(String boardName, Pageable pageable);

    List<Board> findTop4ByBoardNameOrderByIdDesc(String boardName);

    @Query(
            value = "SELECT b FROM Board b WHERE b.subject LIKE %:keyword%",
            countQuery = "SELECT COUNT(b.id) FROM Board b WHERE b.subject LIKE %:keyword"
    )
    Page<Board> findBoardByKeyword(String keyword, Pageable pageable);

    Page<Board> findBoardByOwnerId(Long id, Pageable pageable);

    @Query(value = "SELECT MAX(b.groupOrder) FROM Board b WHERE b.groupId = ?1")
    Long maxGroupOrder(Long groupId);

    @Query(value = "SELECT MIN(b.groupOrder) FROM Board b WHERE b.groupId = ?1 " +
            "and b.groupOrder > ?2 and b.intent <= ?3")
    Long interruptPosition(Long groupId, Long groupOrder, Long intent);

    List<Board> findByGroupId(Long groupId);

}