package com.kfashion.kfashion.profile;

import com.kfashion.kfashion.account.Account;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChangeInfoForm {

    @NotBlank
    @Length(min = 3, max = 28)
    @Pattern(regexp = "^[a-zA-z0-9ㄱ-ㅎ가-힇_-]{3,28}$")
    private String newNickName;

    private String oldNickName;

    private boolean commentPostedByEmail;

    private boolean commentPostedByWeb;

    public ChangeInfoForm(Account account){
        this.newNickName = this.oldNickName = account.getNickName();
    }
}
