package com.kfashion.kfashion.profile;

import com.kfashion.kfashion.account.Account;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeleteAccountForm {

    private Account account;

    private String email;

    @NotBlank
    @Length(min = 8, max = 50)
    private String password;


    DeleteAccountForm(Account account){
        this.email = account.getEmail();
    }
}
