package com.kfashion.kfashion.board;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder @EqualsAndHashCode(of = "id")
public class Board {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String subject;

    @Lob
    private String contents;

    private String images;

    private String nickname;

    private String boardName;

    private LocalDateTime postingTime;

    private Integer view;

    public String getStringPostingTime(){
        return postingTime.format(DateTimeFormatter.ofPattern("MM-dd HH:mm"));
    }

    public void countView(){
        this.view = ++view;
    }

    public void updateBoard(String subject, String contents, String boardName){
        this.subject = subject;
        this.contents = contents;
        this.boardName = boardName;
    }
}
