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

    public void processNewBoard(Account account, BoardForm boardForm) {

        Board board;

        if(boardForm.getGroupId() == null){
            board = Board.builder()
                    .groupId(1L)
                    .groupOrder(1L)
                    .intent(0L)
                    .boardName(boardForm.getBoardName())
                    .nickname(account.getNickName())
                    .subject(boardForm.getSubject())
                    .contents(boardForm.getContents())
                    .image(boardForm.getImage())
                    .postingTime(LocalDateTime.now())
                    .view(0L)
                    .build();
        } else {
            board = Board.builder()
                    .groupId(boardForm.getGroupId())
                    .groupOrder(boardForm.getGroupOrder() + 1)
                    .intent(boardForm.getIntent() + 1)
                    .boardName(boardForm.getBoardName())
                    .nickname(account.getNickName())
                    .subject(boardForm.getSubject())
                    .contents(boardForm.getContents())
                    .image(boardForm.getImage())
                    .postingTime(LocalDateTime.now())
                    .view(0L)
                    .build();
        }

        account.addBoard(board);
        boardRepository.save(board);
    }

    @Transactional(readOnly = true)
    public List<Board> findAllByBoardName(String boardName) {
        return boardRepository.findAllByBoardName(boardName);
    }

    @Transactional(readOnly = true)
    public Board getBoardById(Long id) {
        Optional<Board> board = boardRepository.findById(id);
        board.get().countView();
        return board.orElse(null);
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

    public void deleteBoard(Account account, Long id) {
        account.removeBoard(boardRepository.findById(id));
        boardRepository.deleteById(id);
    }
}
