package com.source.loader.account;

import com.source.loader.account.dtos.SignUpDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AccountService implements UserDetailsService {
    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;
    @Value("${secret.key}")
    String SECRET_KEY;


    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        return accountRepository.findByLogin(login).orElseThrow(() -> new UsernameNotFoundException("No such login exists"));
    }


    public boolean loginIsExist(String login){
        return accountRepository.findByLogin(login).isPresent();
    }

    public boolean registration(SignUpDTO dto){

        return Objects.equals(dto.getSecretKey(), SECRET_KEY) && createNewAccount(dto);
    }

    private boolean createNewAccount(SignUpDTO dto){
        dto.setPassword(passwordEncoder.encode(dto.getPassword()));
        Account account = dto.toEntity(dto);
        accountRepository.save(account);
        return true;
    }

    public Account findUserByLogin(String login) {
        return  accountRepository.findByLogin(login).orElseThrow(() -> new UsernameNotFoundException("No such login exists"));
    }
}
