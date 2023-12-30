package com.source.loader.account;

import com.source.loader.account.dtos.SignInDTO;
import com.source.loader.account.dtos.SignUpDTO;
import com.source.loader.technical.model.attribute.ModelAttributeManager;
import com.source.loader.technical.model.attribute.ModelPageAttributes;
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
    private final ModelAttributeManager modelAttributeManager;

    @GetMapping("/technical/login-page")
    public String getLoginPage(Model model) {
        modelAttributeManager.setModelAttribute(model, ModelPageAttributes.builder()
                .title("Login")
                .content("login-page")
                .entity(new SignInDTO())
                .build());
        return "layout";
    }

    @GetMapping("/technical/registration-page")
    public String getRegistrationPage(Model model) {
        modelAttributeManager.setModelAttribute(model, ModelPageAttributes.builder()
                .title("Sign Up")
                .content("registration-page")
                .entity(new SignUpDTO())
                .build());

        return "layout";
    }

    @PostMapping("/technical/registration")
    public String registration(@Valid @ModelAttribute("entity") SignUpDTO dto,
                               BindingResult bindingResult, Model model) {
        if(bindingResult.hasErrors()){
            modelAttributeManager.setModelAttribute(model, ModelPageAttributes.builder()
                    .title("Sign Up")
                    .content("registration-page")
                    .entity(dto)
                    .build());

            return "layout";
        }

        if(accountService.loginIsExist(dto.getLogin()))
        {
            modelAttributeManager.setModelAttribute(model, ModelPageAttributes.builder()
                    .title("Sign Up")
                    .content("registration-page")
                    .entity(dto)
                    .build());
            bindingResult.rejectValue("login", "error","Login has already used");
            return "layout";
        }

        if(accountService.registration(dto)){
            modelAttributeManager.setModelAttribute(model, ModelPageAttributes.builder()
                    .title("Login")
                    .content("login-page")
                    .entity(new SignInDTO())
                    .build());
        }
        else{
            modelAttributeManager.setModelAttribute(model, ModelPageAttributes.builder()
                    .title("Sign Up")
                    .content("registration-page")
                    .entity(dto)
                    .build());
            bindingResult.rejectValue("secretKey", "error","secret key is wrong");
        }

        return "layout";
    }
}
