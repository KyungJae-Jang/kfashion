package com.kfashion.kfashion.Notification;

import com.kfashion.kfashion.board.Comment;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Async
@Component
@RequiredArgsConstructor
public class CommentEventHandler {

    @EventListener
    public void handleReplyBoardEvent(CommentEvent event){

    }

}
