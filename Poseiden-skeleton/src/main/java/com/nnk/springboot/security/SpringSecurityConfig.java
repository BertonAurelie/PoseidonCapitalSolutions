package com.nnk.springboot.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig {
    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http.authorizeHttpRequests(auth -> {
                    auth.requestMatchers("/login", "/home", "/user/**", "/css/**").permitAll();
                    auth.anyRequest().authenticated();
                })
                .formLogin(form -> form
                        .usernameParameter("username")
                        .defaultSuccessUrl("/curvePoint/list", true)

                )
                .oauth2Login(Customizer.withDefaults())
                .build();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http, BCryptPasswordEncoder bCryptPasswordEncoder) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder
                .userDetailsService(customUserDetailsService)
                .passwordEncoder(bCryptPasswordEncoder);
        return authenticationManagerBuilder.build();
    }
}
