package com.source.loader.account;

import com.source.loader.account.dtos.SignInDTO;
import com.source.loader.account.dtos.SignUpDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/account")
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;

    @GetMapping("/technical/login-page")
    public String getLoginPage(Model model) {
        model.addAttribute("title", "Login")
                .addAttribute("content", "login-page")
                .addAttribute("signIn", SignInDTO.builder().build());
        return "layout";
    }

    @GetMapping("/technical/registration-page")
    public String getRegistrationPage(Model model) {
        model.addAttribute("title", "Sign Up")
                .addAttribute("content", "registration-page")
                .addAttribute("signUp", new SignUpDTO());
        return "layout";
    }

    @PostMapping("/technical/registration")
    public String registration(@Valid @ModelAttribute("signUp") SignUpDTO dto,
                               BindingResult bindingResult, Model model) {
        model.addAttribute("title", "Sign Up");
        if(bindingResult.hasErrors()){
            model.addAttribute("signUp", dto);
            model.addAttribute("content", "registration-page");
            return "layout";
        }

        if(accountService.loginIsExist(dto.getLogin()))
        {
            model.addAttribute("title", "Sign Up");
            bindingResult.rejectValue("login", "error","Login has already used");
            model.addAttribute("content", "registration-page");
            model.addAttribute("signUp", dto);
            return "layout";
        }

        if(accountService.registration(dto)){
            model.addAttribute("title", "Sign In");
            model.addAttribute("content", "login-page");
            model.addAttribute("signIn", new SignInDTO());
        }
        else{
            model.addAttribute("title", "Sign Up");
            bindingResult.rejectValue("secretKey", "error","secret key is wrong");
            model.addAttribute("content", "registration-page");
            model.addAttribute("signUp", dto);
        }

        return "layout";
    }
}
