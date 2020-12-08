package com.kfashion.kfashion.board;

import com.kfashion.kfashion.account.Account;
import com.kfashion.kfashion.account.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    private final AccountRepository accountRepository;

    public void processNewBoard(Account account, BoardForm boardForm) {

        Board board;

        if(boardForm.getGroupId() == null){
            board = Board.builder()
                    .groupOrder(0L)
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
            Long interruptPosition = boardRepository.interruptPosition(boardForm.getGroupId(),
                    boardForm.getGroupOrder(), boardForm.getIntent());

            if(interruptPosition == null){
                board = Board.builder()
                        .groupId(boardForm.getGroupId())
                        .groupOrder(boardRepository.maxGroupOrder(boardForm.getGroupId()) + 1)
                        .intent(boardForm.getIntent() + 1)
                        .boardName(boardForm.getBoardName())
                        .nickname(account.getNickName())
                        .subject(boardForm.getSubject())
                        .contents(boardForm.getContents())
                        .image(boardForm.getImage())
                        .postingTime(LocalDateTime.now())
                        .view(0L)
                        .build();
            } else {
                List<Board> boardList = boardRepository.findByGroupId(boardForm.getGroupId());

                for(Board boards : boardList){
                    if(boards.getGroupOrder() >= interruptPosition){
                        boards.moveGroupOrder();
                    }
                }
                boardRepository.saveAll(boardList);

                board = Board.builder()
                        .groupId(boardForm.getGroupId())
                        .groupOrder(interruptPosition)
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
        }

        Board savedBoard = boardRepository.save(board);
        accountRepository.save(account).addBoard(savedBoard);

        if(board.getGroupId() == null){
            setGroupId(savedBoard);
        }
    }

    private void setGroupId(Board board) {
        board.setGroupId(board.getId());
    }

    @Transactional(readOnly = true)
    public Page<Board> findAllByBoardName(String boardName, Pageable pageable) {
        Page<Board> page = boardRepository.findBoardByBoardName(boardName, pageable);
        return page;
    }

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
        accountRepository.save(account).removeBoard(boardRepository.findById(id));
        boardRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public Page<Board> findBoardByAccount(Account account, Pageable pageable) {
        Page<Board> page = boardRepository.findBoardByOwnerId(account.getId(), pageable);
        return page;
    }
}
