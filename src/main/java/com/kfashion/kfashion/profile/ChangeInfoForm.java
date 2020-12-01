package com.kfashion.kfashion.profile;

import com.kfashion.kfashion.account.Account;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChangeInfoForm {

    private Account account;

    @NotBlank
    @Length(min = 3, max = 28)
    @Pattern(regexp = "^[a-zA-z0-9ㄱ-ㅎ가-힇_-]{3,28}$")
    private String newNickName;

    private String oldNickName;

    private boolean commentPostedByEmail;

    private boolean commentPostedByWeb;

    public ChangeInfoForm(Account account){
        this.newNickName = account.getNickName();
        this.oldNickName = account.getNickName();
        this.commentPostedByEmail = account.isCommentPostedByEmail();
        this.commentPostedByWeb = account.isCommentPostedByWeb();
    }
}
