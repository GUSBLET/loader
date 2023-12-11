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


//    @Bean
//    CorsConfigurationSource corsConfigurationSource() {
//        CorsConfiguration configuration = new CorsConfiguration();
//        configuration.setAllowedOrigins(Arrays.asList("http://localhost:5173/"));
//        configuration.setAllowedMethods(Arrays.asList("GET","POST"));
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", configuration);
//        return source;
//    }


    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .cors(cors -> cors.disable())
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(formLogin -> formLogin
                        .usernameParameter("login")
                        .passwordParameter("password")
                        .loginPage("/account/login-page")
                        .loginProcessingUrl("/authenticate")
                        .defaultSuccessUrl("/account/profile")
                        .permitAll()
                )
                .logout(logout -> logout.logoutSuccessUrl("/account/login-page"))
                .authorizeHttpRequests(authorize -> {
                    authorize.requestMatchers(
                            "/api/**",
                            "/account/login-page"
                    ).permitAll();
                    authorize.requestMatchers(
                            HttpMethod.POST, "/api/get-model-page/**"
                    ).permitAll();
                    authorize
                            .requestMatchers(
                                    "/account/profile",
                                    "/model3d/**",
                                    "/model3d/create-model-form"
                            ).authenticated();
                })
                .build();
    }



    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
