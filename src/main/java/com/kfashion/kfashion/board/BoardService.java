package com.kfashion.kfashion.board;

import com.kfashion.kfashion.account.Account;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;

    public void processNewPost(Account account, BoardForm boardForm) {

        Board board = Board.builder()
                .nickname(account.getNickName())
                .boardName(boardForm.getBoardName())
                .subject(boardForm.getSubject())
                .contents(boardForm.getContents())
                .image(boardForm.getImage())
                .postingTime(LocalDateTime.now())
                .view(0)
                .build();

        boardRepository.save(board);
    }

    @Transactional(readOnly = true)
    public List<Board> findAllPostsByBoardName(String boardName) {
        return boardRepository.findAllPostByBoardName(boardName);
    }

    @Transactional(readOnly = true)
    public Board getBoardById(Long id) {
        Optional<Board> board = boardRepository.findById(id);
        return board.orElse(null);
    }

    public void setBoardCountView(Board board) {
        board.countView();
        boardRepository.save(board);
    }

    public void updateBoard(BoardForm boardForm) {
        Board board = getBoardById(boardForm.getBoardId());

        if(board.getImage().equals(boardForm.getImage())){
            System.out.println("True");
        } else {
            System.out.println("False");
        }

        board.updateBoard(boardForm.getBoardName(), boardForm.getSubject(),
                boardForm.getContents(), boardForm.getImage());
        boardRepository.save(board);
    }

    public void deleteBoard(Long id) {
        boardRepository.deleteById(id);
    }
}
