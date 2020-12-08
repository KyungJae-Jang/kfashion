package com.kfashion.kfashion.board;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class BoardDeleteForm {

    @NotBlank
    private Long boardId;

    @NotBlank
    private String boardName;
}
