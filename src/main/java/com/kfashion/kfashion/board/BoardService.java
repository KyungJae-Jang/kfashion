package com.kfashion.kfashion.board;

import com.kfashion.kfashion.account.Account;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class BoardService {

    private final String UPLOADPATH = "/Users/kyungjae/Documents/Study/IdeaProjects/kfashion/src/main/resources/static/images";
    private final BoardRepository boardRepository;

    public void processNewPost(Account account, BoardForm boardForm, MultipartFile mulFile) {

        String newFileName = setNewFileName(mulFile.getOriginalFilename());
        uploadFile(newFileName, mulFile);

        Board board = Board.builder()
                .nickname(account.getNickName())
                .boardName(boardForm.getBoardName())
                .subject(boardForm.getSubject())
                .contents(boardForm.getContents())
                .images(File.separator + "images" + File.separator + newFileName)
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
        board.updateBoard(boardForm.getBoardName(), boardForm.getSubject(),
                boardForm.getContents(), boardForm.getImages());
        boardRepository.save(board);
    }

    public void deleteBoard(Long id) {
        boardRepository.deleteById(id);
    }

    public void uploadFile(String newFileName, MultipartFile mulFile) {

        File newFile = new File(UPLOADPATH, newFileName);
        try {
            FileCopyUtils.copy(mulFile.getBytes(), newFile);
            mulFile.transferTo(newFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String setNewFileName(String fileName){
        UUID uid = UUID.randomUUID();
        String newFileName = uid + "_" + fileName;

        return newFileName;
    }
}
