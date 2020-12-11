package com.kfashion.kfashion.board;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query(value = "SELECT MAX(c.groupOrder) FROM Comment c WHERE c.groupId = ?1")
    Long maxGroupOrder(Long groupId);

    List<Comment> findByGroupId(Long groupId);

    List<Comment> findBoardByAccountOwnerId(Long id);

    Page<Comment> findAllByBoardOwnerId(Long id, Pageable pageable);

    @Query(value = "SELECT MIN(c.groupOrder) FROM Comment c WHERE c.groupId = ?1 " +
            "and c.groupOrder > ?2 and c.intent <= ?3")
    Long interruptPosition(Long groupId, Long groupOrder, Long intent);
}
