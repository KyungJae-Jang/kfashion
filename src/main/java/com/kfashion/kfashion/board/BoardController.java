package com.kfashion.kfashion.board;

import com.kfashion.kfashion.account.Account;
import com.kfashion.kfashion.account.CurrentUser;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

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
    public String boardDaily(@PageableDefault(size = 12, page = 0, direction = Sort.Direction.ASC)
                                         Pageable pageable, Model model){

        Page<Board> pageList = boardService.findAllByBoardName("daily", pageable);
        model.addAttribute("boardName", "daily");
        model.addAttribute("pageList", pageList);

        return "board/board-daily";
    }

    @GetMapping("/board-fashion")
    public String boardFashion(@PageableDefault(size = 12, page = 0, direction = Sort.Direction.ASC)
                                           Pageable pageable, Model model){

        Page<Board> pageList = boardService.findAllByBoardName("fashion", pageable);
        model.addAttribute("boardName", "fashion");
        model.addAttribute("pageList", pageList);

        return "board/board-fashion";
    }

    @GetMapping("/board-free")
    public String boardFree(@PageableDefault(size = 12, page = 0, sort = {"groupId", "groupOrder"},
                             direction = Sort.Direction.ASC) Pageable pageable, Model model){

        Page<Board> pageList = boardService.findAllByBoardName("free", pageable);
        model.addAttribute("boardName", "free");
        model.addAttribute("pageList", pageList);

        return "board/board-free";
    }

    @GetMapping("/board-sale")
    public String boardSale(@PageableDefault(size = 12, page = 0, sort = {"groupId", "groupOrder"},
                            direction = Sort.Direction.ASC) Pageable pageable, Model model){

        Page<Board> pageList = boardService.findAllByBoardName("sale", pageable);
        model.addAttribute("boardName", "sale");
        model.addAttribute("pageList", pageList);

        return "board/board-sale";
    }

    @GetMapping("/board-view")
    public String boardView(@RequestParam(value = "boardId") Long id,
                            @CurrentUser Account account,  Model model){

        Board board = boardService.getBoardById(id);
        model.addAttribute("board", board);
        model.addAttribute("account", account);

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
    public String boardByAccount(@CurrentUser Account account,
                                 @PageableDefault(size = 12, page = 0, sort = {"groupId", "groupOrder"},
                                         direction = Sort.Direction.ASC)
                                         Pageable pageable, Model model){

        Page<Board> pageList = boardService.findBoardByAccount(account, pageable);
        model.addAttribute("pageList", pageList);

        return "board/board-by-account";
    }
}



// TODO 댓글, 대댓글
// TODO 본인 계정의 글만 수정, 삭제
// TODO delete 쿼리가 수행되지 않는 문제 해결
