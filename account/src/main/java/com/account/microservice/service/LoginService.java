package com.account.microservice.service;

import com.codes.common.dto.LoginRequest;
import com.codes.common.dto.Response;

public interface LoginService {
    Response<String> loginWithJwt(LoginRequest loginRequest);
    Response<String> loginWithBasic(LoginRequest loginRequest);
}
