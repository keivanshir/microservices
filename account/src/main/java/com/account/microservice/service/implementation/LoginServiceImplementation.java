package com.account.microservice.service.implementation;

import com.account.microservice.security.JwtUtil;
import com.account.microservice.service.LoginService;
import com.codes.common.dto.LoginRequest;
import com.codes.common.dto.Response;
import com.codes.common.exception.NotFoundException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImplementation implements LoginService {

    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

    public LoginServiceImplementation(JwtUtil jwtUtil, AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder) {
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Response<String> loginWithJwt(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );
        // ثبت اطلاعات کاربر در SecurityContext
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // تولید و ارسال توکن JWT
        String jwt = jwtUtil.generateToken(loginRequest.getUsername());

        return Response.<String>builder()
                .message("توکن:")
                .statusCode(200)
                .data(jwt)
                .build();
    }

    @Override
    public Response<String> loginWithBasic(LoginRequest loginRequest) {
        return Response.<String>builder()
                .message("ارتباط با موفقیت برقرار شد!")
                .statusCode(200)
                .build();
    }
}
