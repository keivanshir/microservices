package com.codes.common.exception;

import com.codes.common.dto.Response;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
@Slf4j
public class GlobalException {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Response<String>> handleAllException(Exception ex){
        ex.printStackTrace();
        log.error(ex.getMessage());
        Response<String> errorResponse = Response.<String>builder()
                .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message("خطای سرور")
                .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Response<String>> handleNotFoundException(NotFoundException ex){
        ex.printStackTrace();
        log.error(ex.getMessage());
        Response<String> errorResponse = Response.<String>builder()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .message(ex.getMessage())
                .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AccountDisabledException.class)
    public ResponseEntity<Response<String>> handleAccountDisabledException(AccountDisabledException ex){
        ex.printStackTrace();
        log.error(ex.getMessage());
        Response<String> errorResponse = Response.<String>builder()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .message(ex.getMessage())
                .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InsufficientRemainingException.class)
    public ResponseEntity<Response<String>> handleInsufficientRemainingException(InsufficientRemainingException ex){
        ex.printStackTrace();
        log.error(ex.getMessage());
        Response<String> errorResponse = Response.<String>builder()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .message(ex.getMessage())
                .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AccountsEqualException.class)
    public ResponseEntity<Response<String>> handleAccountsEqualException(AccountsEqualException ex){
        ex.printStackTrace();
        log.error(ex.getMessage());
        Response<String> errorResponse = Response.<String>builder()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .message(ex.getMessage())
                .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IDExistsException.class)
    public ResponseEntity<Response<String>> handleIDExistsException(IDExistsException ex){
        ex.printStackTrace();
        log.error(ex.getMessage());
        Response<String> errorResponse = Response.<String>builder()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .message(ex.getMessage())
                .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AccountExistsException.class)
    public ResponseEntity<Response<String>> handleAccountExistsException(AccountExistsException ex){
        ex.printStackTrace();
        log.error(ex.getMessage());
        Response<String> errorResponse = Response.<String>builder()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .message(ex.getMessage())
                .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<Response<String>> handleUserNotFoundException(UsernameNotFoundException ex){
        ex.printStackTrace();
        log.error(ex.getMessage());
        Response<String> errorResponse = Response.<String>builder()
                .statusCode(HttpStatus.UNAUTHORIZED.value())
                .message("نام کاربری یا رمز عبور اشتباه است!")
                .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Response<String>> handleBadCredentialsException(BadCredentialsException ex){
        ex.printStackTrace();
        log.error(ex.getMessage());
        Response<String> errorResponse = Response.<String>builder()
                .statusCode(HttpStatus.UNAUTHORIZED.value())
                .message("نام کاربری یا رمز عبور اشتباه است!")
                .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Response<Map<String, String>>> handleArgumentNotValidException(ConstraintViolationException ex){
        ex.printStackTrace();
        log.error(ex.getMessage());
        Map<String, String> errors = new HashMap<>();

        ex.getConstraintViolations().forEach((error) -> {
            String fieldName = error.getPropertyPath().toString() + "=" + error.getInvalidValue();
            String errorMessage = error.getMessage();
            errors.put(fieldName, errorMessage);
        });

        Response<Map<String, String>> errorResponse = Response.<Map<String, String>>builder()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .message("فیلدهای نامعتبر:")
                .data(errors)
                .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
