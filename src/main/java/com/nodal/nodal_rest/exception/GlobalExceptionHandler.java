package com.nodal.nodal_rest.exception;

import com.nodal.nodal_rest.dto.ApiResponse;
import com.nodal.nodal_rest.libs.ResponseEntityBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse<String>> handleResourceNotFoundException(ResourceNotFoundException exception)
    {
        return ResponseEntityBuilder.notFound(exception.getMessage());
    }
}
