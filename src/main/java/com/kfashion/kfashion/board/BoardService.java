package com.kfashion.kfashion.board;

import com.kfashion.kfashion.account.Account;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    public void processNewPost(Account account, BoardForm boardForm) {
        Board board = Board.builder()
                .nickname(account.getNickName())
                .subject(boardForm.getSubject())
                .contents(boardForm.getContents())
                .image(boardForm.getImage())
                .postingTime(LocalDateTime.now())
                .view(0)
                .build();

        boardRepository.save(board);
    }

    @Transactional(readOnly = true)
    public List<Board> findAllPosts() {
        return boardRepository.findAll();
    }
}
