package com.account.microservice.security;

import com.codes.common.security.CustomUserDetailsService;
import com.codes.common.security.JwtUtil;
import com.codes.common.security.SecurityConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class AccountSecurityConfig extends SecurityConfig {

    public AccountSecurityConfig(CustomUserDetailsService userDetailsService, JwtUtil jwtUtil) {
        super(userDetailsService, jwtUtil);
    }

    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return super.securityFilterChain(http);
    }

}
