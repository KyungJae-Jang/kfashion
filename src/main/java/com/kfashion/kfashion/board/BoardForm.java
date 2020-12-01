package com.kfashion.kfashion.board;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Data
public class BoardForm {

    @NotBlank @Length(min = 1)
    private String subject;

    @NotBlank @Length(min = 1)
    private String contents;

    private String image;
}
