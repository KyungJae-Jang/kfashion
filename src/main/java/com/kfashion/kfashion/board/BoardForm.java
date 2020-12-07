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
    private Long boardId;

    private Long groupId;

    private Long groupOrder;

    private Long intent;

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
        this.image = board.getImage();
        this.boardName = board.getBoardName();
    }

    public BoardForm(String boardName, Long groupId, Long groupOrder, Long intent){
        this.boardName = boardName;
        this.groupId = groupId;
        this.groupOrder = groupOrder;
        this.intent = intent;
    }
}
