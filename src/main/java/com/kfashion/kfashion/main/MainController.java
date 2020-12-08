package com.kfashion.kfashion.main;

import com.kfashion.kfashion.account.Account;
import com.kfashion.kfashion.account.CurrentUser;
import com.kfashion.kfashion.board.Board;
import com.kfashion.kfashion.board.BoardRepository;
import com.kfashion.kfashion.board.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class MainController {

    private final BoardRepository boardRepository;

    @GetMapping("/")
    public String index(@CurrentUser Account account, Model model){
        if(account != null){
            model.addAttribute("account", account);
        }
        return "/index";
    }

    @GetMapping("/login")
    public String login(){
        return "/login";
    }

    @GetMapping("/search/board")
    @Transactional(readOnly = true)
    public String searchBoard(@RequestParam String keyword,
                              @PageableDefault(size = 12, page = 0, direction = Sort.Direction.ASC)
                                Pageable pageable, Model model){

        Page<Board> pageList = boardRepository.findBoardByKeyword(keyword, pageable);
        model.addAttribute("pageList", pageList);
        model.addAttribute("keyword", keyword);

        return "/search/search-result";
    }
}
