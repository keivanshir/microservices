package com.account.microservice.controller;

import com.account.microservice.service.UserService;
import com.codes.common.dto.Response;
import com.codes.common.dto.UserDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @Operation(summary = "creates user",
            responses = {
                    @ApiResponse(responseCode = "201", description = "user created")
            })
    @PostMapping("/add")
    public ResponseEntity<Response<UserDto>> createUser(@RequestBody UserDto userDto) {
        return new ResponseEntity<>(userService.createUser(userDto), HttpStatus.CREATED);
    }

    @Operation(summary = "updates user",
            responses = {
                    @ApiResponse(responseCode = "201", description = "user updated")
            })
    @PutMapping("/update/{id}")
    public ResponseEntity<Response<UserDto>> updateUser(@PathVariable Long id, @RequestBody UserDto userDto) {
        return new ResponseEntity<>(userService.updateUser(id, userDto), HttpStatus.CREATED);
    }

    @Operation(summary = "deletes user",
            responses = {
                    @ApiResponse(responseCode = "200", description = "user deleted"),
                    @ApiResponse(responseCode = "404", description = "user not found")
            })
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Response<UserDto>> deleteUser(@PathVariable Long id) {
        return new ResponseEntity<>(userService.deleteUser(id), HttpStatus.OK);
    }

}
