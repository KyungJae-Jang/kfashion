package com.kfashion.kfashion.board;

import com.kfashion.kfashion.account.Account;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder @EqualsAndHashCode(of = "id")
public class Comment {

    @Id @GeneratedValue
    private Long id;

    private Long groupId;

    private Long groupOrder;

    private Long intent;

    private String parentNickName;

    private String nickName;

    private String commentTime;

    private String comment;

    @ManyToOne(fetch = FetchType.LAZY)
    private Board boardOwner;

    @ManyToOne(fetch = FetchType.LAZY)
    private Account accountOwner;

    public void moveGroupOrder(){
        this.groupOrder += 1;
    }

}
