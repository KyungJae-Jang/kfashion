package com.kfashion.kfashion.Notification;

import com.kfashion.kfashion.board.Comment;
import lombok.Getter;

@Getter
public class CommentEvent {

    private Comment comment;

    public CommentEvent(Comment comment){
        this.comment = comment;
    }
}
