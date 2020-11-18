package com.kfashion.kfashion.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class AccountController {

    @Autowired
    SignUpValidator signUpValidator;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    JavaMailSender javaMailSender;


    @InitBinder
    public void initBinder(WebDataBinder webDataBinder){
        webDataBinder.addValidators(signUpValidator);
    }

    @GetMapping("/sign-up")
    public String signUp(Model model){
        model.addAttribute("signUpForm", new SignUpForm());
        return "account/sign-up";
    }

    @PostMapping("/sign-up")
    public String signUpForm(@Valid SignUpForm signUpForm, Errors errors, Model model){
        if(errors.hasErrors()){
            model.addAttribute("signUpForm", signUpForm);
            return "account/sign-up";
        }

        Account account = Account.builder()
                .nickName(signUpForm.getNickname())
                .email(signUpForm.getEmail())
                .password(signUpForm.getPassword()) // TODO Creating Encoder
                .build();

        Account newAccount = accountRepository.save(account);
        newAccount.generateCheckToken();

        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setSubject("K-Fashion 인증메일");
        msg.setText("/checked-email?email=" + newAccount.getNickName()
                    + "&token=" + newAccount.getEmailCheckToken());

        javaMailSender.send(msg);


        return "redirect:/";
    }

    @GetMapping("/checked-email")
    public String checkedEmail(){

        return "account/checked-email";
    }
}
