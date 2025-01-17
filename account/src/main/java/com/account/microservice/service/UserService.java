package com.account.microservice.service;

import com.codes.common.dto.Response;
import com.codes.common.dto.UserDto;

public interface UserService {

    Response<UserDto> createUser(UserDto userDto);

    Response<UserDto> updateUser(Long id, UserDto userDto);

    Response<UserDto> deleteUser(Long userId);

}
