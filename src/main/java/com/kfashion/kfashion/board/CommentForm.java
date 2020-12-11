package com.kfashion.kfashion.board;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentForm {

    @NotEmpty
    private Long boardId;

    @NotEmpty
    private Long commentId;

    private Long accountId;

    @NotEmpty
    private String comment;

    private String parentNickName;

    private String nickName;

    private String commentTime;

    private Long groupId;

    private Long groupOrder;

    private Long intent;
}
