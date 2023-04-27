package com.api.exceptions;

import com.api.payloads.apiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class allExceptionHandler {

    @ExceptionHandler(resourceNotFoundException.class)
    public ResponseEntity<apiResponse> resourceNotFoundExceptionHandler(resourceNotFoundException ex){
        String message = ex.getMessage();
        apiResponse apiResponse = new apiResponse(message, false);
        return new ResponseEntity<>(apiResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex){
        Map<String, String> response = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error->{
            String field = ((FieldError)error).getField();
            String message = error.getDefaultMessage();
            response.put(field, message);
        });
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(apiException.class)
    public ResponseEntity<apiResponse> handleApiException(apiException ex){
        String message = ex.getMessage();
        apiResponse apiResponse = new apiResponse(message, true);
        return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
    }
}
