package com.account.microservice.controller;

import com.account.microservice.security.JwtUtil;
import com.account.microservice.service.LoginService;
import com.codes.common.dto.LoginRequest;
import com.codes.common.dto.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Value;

@RestController
@RequestMapping("auth/login")
public class LoginController {


    private final LoginService loginService;

    @Value("${app.auth.type}")
    private String authType;

    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @Operation(summary = "login for JWT token",
            responses = {
                    @ApiResponse(responseCode = "201", description = "JWT token created")
            })
    @PostMapping
    public ResponseEntity<Response<String>> login(@RequestBody LoginRequest loginRequest){
        if ("JWT".equalsIgnoreCase(authType)) {
            return new ResponseEntity<>(loginService.loginWithJwt(loginRequest), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(loginService.loginWithBasic(loginRequest), HttpStatus.OK);
        }
    }
}
