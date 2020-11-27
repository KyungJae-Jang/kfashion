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

    @Autowired
    Account account;

    @NotBlank
    @Length(min = 3, max = 28)
    @Pattern(regexp = "^[a-zA-z0-9ㄱ-ㅎ가-힇_-]{3,28}$")
    private String nickname;

    private String email;

    private boolean commentPostedByEmail;

    private boolean commentPostedByWeb;

    public ChangeInfoForm(Account account){
        this.nickname = account.getNickName();
        this.email = account.getEmail();
        this.commentPostedByEmail = account.isCommentPostedByEmail();
        this.commentPostedByWeb = account.isCommentPostedByWeb();
    }

}
