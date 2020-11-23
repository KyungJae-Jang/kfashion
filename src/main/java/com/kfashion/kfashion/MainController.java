package com.kfashion.kfashion;

import com.kfashion.kfashion.account.Account;
import com.kfashion.kfashion.account.CurrentUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/")
    public String index(@CurrentUser Account account, Model model){
        if(account != null){
            model.addAttribute("account", account);
        }
        return "/index";
    }
}
