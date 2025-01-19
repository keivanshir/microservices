package com.codes.common.dto;

import com.codes.common.enums.UserRole;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDto{
    private Long id;
    private String name;
    private String email;
    private String password;
    private UserRole role;
}
