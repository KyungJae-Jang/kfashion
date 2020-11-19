package com.kfashion.kfashion.account;

import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired SignUpValidator signUpValidator;
    @Autowired AccountService accountService;
    @Autowired AccountRepository accountRepository;


    @InitBinder
    public void initBinder(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(signUpValidator);
    }

    @GetMapping("/sign-up")
    public String signUp(Model model) {
        model.addAttribute("signUpForm", new SignUpForm());
        return "account/sign-up";
    }

    @PostMapping("/sign-up")
    public String signUpForm(@Valid SignUpForm signUpForm, Errors errors, Model model) {
        if (errors.hasErrors()) {
            model.addAttribute("signUpForm", signUpForm);
            return "account/sign-up";
        }
        accountService.processNewAccount(signUpForm);

        return "redirect:/";
    }

    @GetMapping("/check-email-token")
    public String checkEmail(String email, String token, Model model) {
        Account account = accountRepository.findByEmail(email);
        String view = "account/checked-email";
        if(!accountRepository.existsByEmail(email)) {
            model.addAttribute("error", "wrong.email");
            return view;
        }
        if(!account.getEmailCheckToken().equals(token)){
            model.addAttribute("error", "wrong.token");
            return view;
        }

        model.addAttribute("email", account.getEmail());
        return view;
    }
}
