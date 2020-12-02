package com.kfashion.kfashion.board;

import com.kfashion.kfashion.account.Account;
import com.kfashion.kfashion.account.CurrentUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @GetMapping("/board-write")
    public String boardWrite(Model model){
        model.addAttribute("boardForm", new BoardForm());
        return "board/board-write";
    }

    @PostMapping("/board-write")
    public String boardWriteForm(@CurrentUser Account account,
                                  @Valid BoardForm boardForm, Errors errors,
                                  Model model){
        if(errors.hasErrors()){
            model.addAttribute("boardForm", boardForm);
            return "board/board-write";
        }

        boardService.processNewPost(account, boardForm);

        return "redirect:/board-free?boardName=" + boardForm.getBoardName();
    }

    @GetMapping("/board-free")
    public String boardFree(@RequestParam(value = "boardName") String boardName, Model model){

        List<Board> boardList = boardService.findAllPostsByBoardName(boardName);
        model.addAttribute("boardList", boardList);

        return "board/board-free";
    }

    @GetMapping("/board-view")
    public String boardView(@RequestParam(value = "boardId") Long id, Model model){

        Board board = boardService.getBoardById(id);
        boardService.setBoardCountView(board);
        model.addAttribute("board", board);

        return "board/board-view";
    }

    @GetMapping("/board-update")
    public String boardUpdate(@RequestParam(value = "boardId") Long id, Model model){

        Board board = boardService.getBoardById(id);
        model.addAttribute("boardForm", new BoardForm(board));

        return "board/board-update";
    }

    @PostMapping("/board-update")
    public String boardUpdate(@Valid BoardForm boardForm, Errors errors,
                              Model model){

        if(errors.hasErrors()){
            model.addAttribute("boardForm", boardForm);
            return "board/board-update";
        }

        boardService.updateBoard(boardForm);

        return "redirect:/board-view?boardId=" + boardForm.getBoardId();
    }

    @GetMapping("/board-delete")
    public String boardDelete(@RequestParam(value = "boardId") Long id,
                              @RequestParam(value = "boardName") String boardName){

        boardService.deleteBoard(id);

        return "redirect:/board-free?boardName=" + boardName;
    }

    @GetMapping("/board-sale")
    public String boardList(@RequestParam(value = "boardName") String boardName, Model model){

        List<Board> boardList = boardService.findAllPostsByBoardName(boardName);
        model.addAttribute("boardList", boardList);

        return "board/board-sale";
    }
}


// TODO 모달 추가 본문이 띄어쓰기가 되도록 수정, 최종 점검 및 커밋
// TODO View 리팩토링, 조회뷰, 업데이트뷰, 게시글 수정, 삭제
// TODO boardWriter footer 겹침 고치기
// TODO 답글, 댓글, 대댓글, 페이징, 검색, 사진파일 업로드