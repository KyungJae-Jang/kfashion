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

    @GetMapping("/board-rewrite")
    public String boardReWrite(@RequestParam(value = "boardName") String boardName,
                               @RequestParam(value = "boardGroupId") Long boardGroupId,
                               @RequestParam(value = "boardGroupOrder") Long boardGroupOrder,
                               @RequestParam(value = "boardIntent") Long boardIntent , Model model){
        model.addAttribute("boardForm",
                new BoardForm(boardName, boardGroupId, boardGroupOrder, boardIntent));
        model.addAttribute("rewrite", true);

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
        boardService.processNewBoard(account, boardForm);

        return "redirect:/board-" + boardForm.getBoardName();
    }

    @GetMapping("/board-daily")
    public String boardDaily(Model model){

        List<Board> boardList = boardService.findAllByBoardName("daily");
        model.addAttribute("boardList", boardList);

        return "board/board-daily";
    }

    @GetMapping("/board-fashion")
    public String boardFashion(Model model){

        List<Board> boardList = boardService.findAllByBoardName("fashion");
        model.addAttribute("boardList", boardList);

        return "board/board-fashion";
    }

    @GetMapping("/board-free")
    public String boardFree(Model model){

        List<Board> boardList = boardService.findAllByBoardName("free");
        model.addAttribute("boardList", boardList);

        return "board/board-free";
    }

    @GetMapping("/board-sale")
    public String boardSale(Model model){

        List<Board> boardList = boardService.findAllByBoardName("sale");
        model.addAttribute("boardList", boardList);

        return "board/board-sale";
    }

    @GetMapping("/board-view")
    public String boardView(@RequestParam(value = "boardId") Long id, Model model){

        Board board = boardService.getBoardById(id);
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
                              @RequestParam(value = "boardName") String boardName,
                              @CurrentUser Account account){

        boardService.deleteBoard(account, id);

        return "redirect:/board-" + boardName;
    }

    @GetMapping("/board-by-account")
    public String boardByAccount(@CurrentUser Account account,  Model model){

        List<Board> boardList = account.getBoardList();
        model.addAttribute("boardList", boardList);

        return "board/board-by-account";
    }
}



// TODO 댓글, 대댓글, 페이징, 검색,