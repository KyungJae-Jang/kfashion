package com.kfashion.kfashion.board;

import com.kfashion.kfashion.account.Account;
import com.kfashion.kfashion.account.CurrentUser;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/comment-write")
    public @ResponseBody void CommentWrite(@CurrentUser Account account, CommentForm commentForm){

        commentService.processNewComment(account, commentForm);
    }

    @GetMapping("/comment-list")
    public @ResponseBody Map<String,Object> CommentList(CommentForm commentForm,
                                                        @SortDefault.SortDefaults({
                                                            @SortDefault(sort = "groupId", direction = Sort.Direction.DESC),
                                                            @SortDefault(sort = "groupOrder", direction = Sort.Direction.ASC)})
                                                           Pageable pageable){

        return commentService.getAllCommentList(commentForm, pageable);
    }

    @PostMapping("/comment-update")
    public @ResponseBody void CommentUpdate(CommentForm commentForm){

        commentService.updateComment(commentForm);
    }

    @PostMapping("/comment-delete")
    public @ResponseBody void CommentDelete(@CurrentUser Account account, CommentForm commentForm){

        commentService.deleteComment(account, commentForm);
    }

    @GetMapping("/comment-by-account")
    public String commentByAccount(@CurrentUser Account account,
                                 @PageableDefault(size = 12)
                                 @SortDefault.SortDefaults({
                                         @SortDefault(sort = "groupId", direction = Sort.Direction.DESC),
                                         @SortDefault(sort = "groupOrder", direction = Sort.Direction.ASC)})
                                         Pageable pageable, Model model){

        Page<Comment> pageList = commentService.findCommentByAccount(account, pageable);
        model.addAttribute("pageList", pageList);

        return "comment/comment-by-account";
    }
}
