package com.codes.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Response<T>{

    private int statusCode;
    private String message;
    private final LocalDateTime timestamp = LocalDateTime.now();
    private T data;
}
