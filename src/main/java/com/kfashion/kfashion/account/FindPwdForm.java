package com.kfashion.kfashion.account;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
public class FindPwdForm {

    @NotBlank
    @Email
    private String email;

}

