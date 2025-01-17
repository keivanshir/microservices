package com.account.microservice.service.implementation;


import com.account.microservice.repository.UserRepository;
import com.account.microservice.service.UserService;
import com.codes.common.dto.Mapper;
import com.codes.common.dto.Response;
import com.codes.common.dto.UserDto;
import com.codes.common.entity.User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImplementation implements UserService {

    private final UserRepository userRepository;
    private final Mapper mapper;

    public UserServiceImplementation(UserRepository userRepository, Mapper mapper) {
        this.userRepository = userRepository;
        this.mapper = mapper;
    }

    @Override
    public Response<UserDto> createUser(UserDto userDto) {
        User user = mapper.mapToUser(userDto);
        UserDto savedUserDto = mapper.mapToUserDto(userRepository.save(user));
        return Response.<UserDto>builder()
                .message("کاربر با موفقیت ذخیره شد.")
                .statusCode(201)
                .data(savedUserDto)
                .build();
    }

    @Override
    public Response<UserDto> updateUser(Long id, UserDto userDto) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()){
            UserDto updatedUserDto = mapper.mapToUserDto(userRepository.save(mapper.mapToUser(userDto)));
            return Response.<UserDto>builder()
                    .statusCode(200)
                    .message("کاربر با موفقیت ویرایش شد.")
                    .data(updatedUserDto)
                    .build();
        } else {
            return createUser(userDto);
        }
    }

    @Override
    public Response<UserDto> deleteUser(Long userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        Response<UserDto> response = Response.<UserDto>builder().build();
        if (optionalUser.isPresent()){
            userRepository.delete(optionalUser.get());
            response.setMessage("کاربر با موفقیت حذف شد!");
            response.setStatusCode(200);
        } else {
            response.setMessage("کاربری با این شناسه پیدا نشد!");
            response.setStatusCode(404);
        }
        return response;
    }
}
