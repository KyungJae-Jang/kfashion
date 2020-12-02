package com.kfashion.kfashion.board;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BoardForm {

    private Board board;

    private Long boardId;

    @NotBlank @Length(min = 1)
    private String subject;

    @NotBlank @Length(min = 1)
    private String contents;

    private String image;

    private String boardName;

    public BoardForm(Board board){
        this.boardId = board.getId();
        this.subject = board.getSubject();
        this.contents = board.getContents();
        this.image = board.getImages();
        this.boardName = board.getBoardName();
    }
}
