package com.source.loader.configs;


import com.source.loader.account.AccountService;
import jakarta.servlet.Filter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.RequestCacheConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    @Bean
    @Order(0)
    SecurityFilterChain resources(HttpSecurity http) throws Exception {
        return http
                .securityMatcher("/images/**", "/js/**", "/css/**", "/**.css", "/**.js")
                .authorizeHttpRequests(c -> c.anyRequest().permitAll())
                .securityContext(AbstractHttpConfigurer::disable)
                .sessionManagement(AbstractHttpConfigurer::disable)
                .requestCache(RequestCacheConfigurer::disable)
                .build();
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(formLogin -> formLogin
                        .usernameParameter("login")
                        .passwordParameter("password")
                        .loginPage("https://puppetpalm.com:9999/account/technical/login-page")
                        .loginProcessingUrl("/authenticate")
                        .defaultSuccessUrl("https://puppetpalm.com:9999/")
                        .permitAll()
                ).sessionManagement(s -> {
                    s.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED);
                })
                .logout(logout -> logout.logoutUrl("/account/technical/logout")
                        .logoutSuccessUrl("https://puppetpalm.com:9999/account/technical/login-page"))
                .exceptionHandling(e -> e.accessDeniedPage("/"))
                .authorizeHttpRequests(authorize -> {
                    authorize.requestMatchers(
                            "/api/**",
                            "/files/**",
                            "/background-api/**",
                            "/",
                            "/swagger/**",
                            "/v3/**",
                            "/account/technical/**",
                            "/swagger-ui/**"
                    ).permitAll();
                    authorize.requestMatchers(
                            "/model3d/**",
                            "/camera-point/**",
                            "/showcase-background/**",
                            "/model3d/create-model-form",
                            "/model3d/update-model-form",
                            "/model3d/show-models**"
                    ).authenticated();
                })
                .build();
    }


    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
