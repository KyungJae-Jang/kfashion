package com.kfashion.kfashion.account;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
public class SignUpForm {

    @NotBlank
    @Length(min = 3, max = 28)
    @Pattern(regexp = "^[a-z0-9ㄱ-ㅎ가-힇_-]{3,28}$")
    private String nickname;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Length(min = 5, max = 50)
    private String password;

}
