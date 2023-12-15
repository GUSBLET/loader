package com.source.loader.account.dtos;

import com.source.loader.account.Account;
import com.source.loader.account.Role;
import com.source.loader.account.validator.PasswordMatch;
import com.source.loader.mapper.Mapper;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@PasswordMatch(password = "password", passwordConfirm = "passwordConfirm")
public class SignUpDTO implements Mapper<SignUpDTO, Account> {

    @NotBlank(message = "Enter your login")
    private String login;

    @NotBlank(message = "Enter password")
    private String password;

    @NotBlank(message = "Confirm password")
    private String passwordConfirm;

    @NotBlank(message = "Enter secretKey")
    private String secretKey;

    @Override
    public SignUpDTO toDto(Account entity) {
        return null;
    }

    @Override
    public Account toEntity(SignUpDTO dto) {
        return Account.builder()
                .login(dto.getLogin())
                .password(dto.password)
                .role(Role.Admin)
                .build();
    }
}
