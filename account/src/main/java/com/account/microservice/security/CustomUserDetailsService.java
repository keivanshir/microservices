package com.account.microservice.security;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final Map<String, UserDetails> users = new HashMap<>();
    private final UserConfig userConfig;

    public CustomUserDetailsService(UserConfig userConfig) {
        this.userConfig = userConfig;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(12);
        for (var user : userConfig.getUsers()) {
            String encodedPassword = passwordEncoder.encode(user.getPassword());
            users.put(user.getUsername(), new User(
                    user.getUsername(),
                    encodedPassword,
                    user.getRoles().stream()
                            .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                            .collect(Collectors.toList())
            ));
        }

        if (!users.containsKey(username)) {
            throw new UsernameNotFoundException("نام کاربری یا رمز عبور اشتباه است!");
        }
        return users.get(username);
    }
}
