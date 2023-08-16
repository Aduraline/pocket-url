package com.thattechguy.pocketurl.exception;

import com.thattechguy.pocketurl.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ApiResponse> handleEmailAlreadyExistsException(UserNotFoundException e) {
        ApiResponse response = new ApiResponse();
        response.setSuccess(false);
        response.setMessage(e.getMessage());
        response.setData(new HashMap<>());

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse> handleGenericException(Exception e) {
        System.out.println(e);
        ApiResponse response = new ApiResponse();
        response.setSuccess(false);
        response.setMessage("Internal server error");
        response.setData(new HashMap<>());

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(response);
    }
}
