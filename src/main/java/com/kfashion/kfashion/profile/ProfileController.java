package com.kfashion.kfashion.profile;

import com.kfashion.kfashion.account.Account;
import com.kfashion.kfashion.account.CurrentUser;
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
public class ProfileController {
    private final ChangeInfoFormValidator changeInfoFormValidator;
    private final ResetPwdFormValidator resetPwdFormValidator;
    private final DeleteAccountFormValidator deleteAccountFormValidator;
    private final ProfileService profileService;

    @InitBinder("changeInfoForm")
    public void changeInfoInitBinder(WebDataBinder webDataBinder){
        webDataBinder.addValidators(changeInfoFormValidator);
    }

    @InitBinder("resetPwForm")
    public void resetPwInitBinder(WebDataBinder webDataBinder){
        webDataBinder.addValidators(resetPwdFormValidator);
    }

    @InitBinder("deleteAccountForm")
    public void deleteAccountInitBinder(WebDataBinder webDataBinder){
        webDataBinder.addValidators(deleteAccountFormValidator);
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
        model.addAttribute("resetPwdForm", new ResetPwdForm());
        return "profile/change-password";
    }

    @PostMapping("/profile/change-password")
    public String changePasswordForm(@CurrentUser Account account,
                                     @Valid ResetPwdForm resetPwdForm,
                                     Errors errors, Model model){
        if(errors.hasErrors()){
            model.addAttribute("account", account);
            model.addAttribute("resetPwdForm", resetPwdForm);
            return "profile/change-password";
        }

        profileService.updatePassword(account, resetPwdForm);
        return "redirect:/profile/account-info";
    }

    @GetMapping("/profile/delete-account")
    public String deleteAccount(@CurrentUser Account account, Model model){
        model.addAttribute("account", account);
        model.addAttribute("deleteAccountForm", new DeleteAccountForm(account));
        return "profile/delete-account";
    }

    @PostMapping("/profile/delete-account")
    public String deleteAccountForm(@CurrentUser Account account,
                                    @Valid DeleteAccountForm deleteAccountForm,
                                    Errors errors, Model model){
        if(errors.hasErrors()){
            model.addAttribute("account", account);
            model.addAttribute("deleteAccountForm", deleteAccountForm);
            return "profile/delete-account";
        }

        profileService.deleteAccount(deleteAccountForm);
        return "auto-logout";
    }
}
