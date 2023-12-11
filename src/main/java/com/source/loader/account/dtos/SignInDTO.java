package com.source.loader.account.dtos;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SignInDTO {

    @NotBlank(message = "Enter your login")
    private String login;

    @NotBlank(message = "Enter your password")
    private String password;
}
