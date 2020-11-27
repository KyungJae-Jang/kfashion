package com.kfashion.kfashion.profile;

import com.kfashion.kfashion.account.Account;
import com.kfashion.kfashion.account.CurrentUser;
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
public class ProfileController {

    @Autowired
    ChangeInfoFormValidator changeInfoFormValidator;

    @Autowired
    PasswordFormValidator passwordFormValidator;

    @Autowired
    ProfileService profileService;

    @InitBinder("changeInfoForm")
    public void changeInfoInitBinder(WebDataBinder webDataBinder){
        webDataBinder.addValidators(changeInfoFormValidator);
    }

    @InitBinder("passwordForm")
    public void passwordInitBinder(WebDataBinder webDataBinder){
        webDataBinder.addValidators(passwordFormValidator);
    }

    @GetMapping("/profile/account-info")
    public String accountInfo(@CurrentUser Account account, Model model){
        model.addAttribute("account", account);
        return "profile/account-info";
    }

    @GetMapping("/profile/change-info")
    public String changeInfo(@CurrentUser Account account, Model model){
        model.addAttribute("account", account);
        model.addAttribute("changeInfoForm", new ChangeInfoForm(account));
        return "profile/change-info";
    }

    @PostMapping("/profile/change-info")
    public String changeInfoForm(@CurrentUser Account account,
                                 @Valid ChangeInfoForm changeInfoForm,
                                 Errors errors, Model model){
        if(errors.hasErrors()){
            model.addAttribute("account", account);
            model.addAttribute("changeInfoForm", changeInfoForm);
            return "profile/change-info";
        }

        profileService.updateInfo(account, changeInfoForm);
        return "redirect:/profile/account-info";
    }

    @GetMapping("/profile/change-password")
    public String changePassword(@CurrentUser Account account, Model model){
        model.addAttribute("account", account);
        model.addAttribute("passwordForm", new PasswordForm());
        return "profile/change-password";
    }

    @PostMapping("/profile/change-password")
    public String changePasswordForm(@CurrentUser Account account,
                                     @Valid PasswordForm passwordForm,
                                     Errors errors, Model model){
        if(errors.hasErrors()){
            model.addAttribute("account", account);
            model.addAttribute("passwordForm", passwordForm);
            return "profile/change-password";
        }

        profileService.updatePassword(account, passwordForm);
        return "redirect:/profile/account-info";
    }

}
