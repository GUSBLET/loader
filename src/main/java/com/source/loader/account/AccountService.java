package com.source.loader.account;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountService implements UserDetailsService {
    private final AccountRepository accountRepository;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        return accountRepository.findByLogin(login).orElseThrow(() -> new UsernameNotFoundException("No such login exists"));
    }

    public Account findUserByLogin(String login) {
        return  accountRepository.findByLogin(login).orElseThrow(() -> new UsernameNotFoundException("No such login exists"));
    }
}
