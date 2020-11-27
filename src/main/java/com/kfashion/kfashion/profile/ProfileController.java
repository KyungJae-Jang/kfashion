package com.kfashion.kfashion.profile;

import com.kfashion.kfashion.account.Account;
import com.kfashion.kfashion.account.CurrentUser;
import org.dom4j.rule.Mode;
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
    ProfileService profileService;

    @InitBinder("changeInfoForm")
    public void changeInfoInitBinder(WebDataBinder webDataBinder){
        webDataBinder.addValidators(changeInfoFormValidator);
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

        account.setNickName(changeInfoForm.getNickname());
        account.setCommentPostedByEmail(changeInfoForm.isCommentPostedByEmail());
        account.setCommentPostedByWeb(changeInfoForm.isCommentPostedByWeb());

        profileService.updateProfile(account);

        return "redirect:/";
    }

}
