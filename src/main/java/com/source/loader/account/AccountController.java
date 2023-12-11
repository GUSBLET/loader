package com.source.loader.account;

import com.source.loader.account.dtos.SignInDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/account")
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;

    @GetMapping("/login-page")
    public String getLoginPage(Model model) {
        model.addAttribute("title", "Login")
                .addAttribute("content", "account/login-page")
                .addAttribute("signIn", SignInDTO.builder().login("name").build());
        return "/account/login";
    }

    @GetMapping("/profile")
    private String getControllerPanel(Model model,  Authentication authentication){
        Account account = accountService.findUserByLogin(authentication.getName());
        model.addAttribute("account", account);
        model.addAttribute("title", "profile");

        return "account/profile";
    }



}
