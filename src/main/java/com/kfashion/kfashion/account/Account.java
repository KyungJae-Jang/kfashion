package com.kfashion.kfashion.account;

import com.kfashion.kfashion.board.Board;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Entity
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder @EqualsAndHashCode(of ="id")
public class Account {

    @Id @GeneratedValue
    private Long id;

    @Column(unique = true)
    private String email;

    @Column(unique = true)
    private String nickName;

    private String password;

    private LocalDateTime joinedAt;

    private LocalDateTime confirmDeadLine;

    private boolean emailVerified;  // TODO 미인증시 자동 탈퇴

    private String emailCheckToken;

    private boolean commentPostedByEmail;

    private boolean commentPostedByWeb;

    @OneToMany(mappedBy = "owner", fetch = FetchType.EAGER)
    private List<Board> boardList;

    public void generateCheckToken() {
        this.emailCheckToken = UUID.randomUUID().toString();
    }

    public String getStringJoinedAt(){
        return joinedAt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    public void updatePassword(String password){
        this.password = password;
    }

    public void updateInfo(String nickName, boolean commentPostedByEmail, boolean commentPostedByWeb){
        this.nickName = nickName;
        this.commentPostedByEmail = commentPostedByEmail;
        this.commentPostedByWeb = commentPostedByWeb;
    }

    public void addBoard(Board board){
        if(boardList == null){
            boardList = new ArrayList<>();
        }
        this.getBoardList().add(board);
        board.setOwner(this);
    }

    public void removeBoard(Optional<Board> board){
        this.getBoardList().remove(board);
        board.get().setOwner(null);
    }
}
