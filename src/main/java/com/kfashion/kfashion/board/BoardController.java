package com.kfashion.kfashion.board;

import com.kfashion.kfashion.account.Account;
import com.kfashion.kfashion.account.CurrentUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;

    @GetMapping("/free-boardWriter")
    public String boardWriter(Model model){
        model.addAttribute("boardForm", new BoardForm());
        return "board/free-boardWriter";
    }

    @PostMapping("/free-boardWriter")
    public String boardWriterForm(@CurrentUser Account account,
                                  @Valid BoardForm boardForm, Errors errors,
                                  Model model){
        if(errors.hasErrors()){
            model.addAttribute("boardForm", boardForm);
            return "board/free-boardWriter";
        }

        boardService.processNewPost(account, boardForm);

        return "redirect:/free-boardList";
    }

    @GetMapping("/free-boardList")
    public String boardList(Model model){

        List<Board> boardList = boardService.findAllPosts();
        model.addAttribute("boardList", boardList);

        return "board/free-boardList";
    }
}
