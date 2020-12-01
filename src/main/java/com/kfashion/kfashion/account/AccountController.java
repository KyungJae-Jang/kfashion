package com.kfashion.kfashion.account;

import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class AccountController {

    private final SignUpFormValidator signUpFormValidator;
    private final FindPwdFormValidator findPwdFormValidator;
    private final AccountService accountService;
    private final AccountRepository accountRepository;


    @InitBinder("signUpForm")
    public void signUpInitBinder(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(signUpFormValidator);
    }

    @InitBinder("findPwdForm")
    public void findPwdInitBinder(WebDataBinder webDataBinder){
        webDataBinder.addValidators(findPwdFormValidator);
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

        Account newAccount = accountService.processNewAccount(signUpForm);
        accountService.login(newAccount);
        return "redirect:/";
    }

    @GetMapping("/check-email-token")
    public String checkEmailToken(String email, String token, Model model) {
        Account account = accountRepository.findByEmail(email);
        String view = "account/checked-email";
        if (account == null) {
            model.addAttribute("error", "wrong.email");
            return view;
        }
        if (!account.getEmailCheckToken().equals(token)) {
            model.addAttribute("error", "wrong.token");
            return view;
        }
        model.addAttribute("email", account.getEmail());
        accountService.completeLogin(account);

        return view;
    }

    @GetMapping("/check-email")
    public String checkEmail(@CurrentUser Account account, Model model) {
        if (account != null) {
            model.addAttribute("email", account.getEmail());
        }

        return "account/check-email";
    }

    @GetMapping("/resend-email")
    public String resendEmail(@CurrentUser Account account, Model model) {
        if(account != null){
            accountService.sendCheckEmailToken(account);
            model.addAttribute("email", account.getEmail());
        }
        return "redirect:/";
    }

    @GetMapping("/find-password")
    public String findPwd(Model model){
        model.addAttribute("findPwdForm", new FindPwdForm());
        return "account/find-password";
    }

    @PostMapping("/find-password")
    public String findPwdForm(@Valid FindPwdForm findPwdForm,
                                     Errors errors, Model model){
        if(errors.hasErrors()){
            model.addAttribute("findPwdForm", findPwdForm);
            return "account/find-password";
        }
        accountService.sendPwdEmailToken(findPwdForm);
        return "redirect:/";
    }

    @GetMapping("/pwd-email-token")
    public String pwdEmailToken(String email, String token, Model model){
        Account account = accountRepository.findByEmail(email);
        String view = "account/found-password";

        if (account == null) {
            model.addAttribute("error", "wrong.email");
            return view;
        }
        if (!account.getEmailCheckToken().equals(token)) {
            model.addAttribute("error", "wrong.token");
            return view;
        }

        model.addAttribute("email", account.getEmail());
        accountService.login(account);
        return view;
    }
}
