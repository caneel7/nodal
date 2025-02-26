package com.nodal.nodal_rest.libs;

import com.nodal.nodal_rest.dto.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseEntityBuilder {

    public static <T> ResponseEntity<ApiResponse<T>> success(T data, String message) {
        ApiResponse<T> response = new ApiResponse<>(true, message, data);
        return ResponseEntity.ok(response);
    }

    public static <T> ResponseEntity<ApiResponse<T>> badRequest(String message) {
        ApiResponse<T> response = new ApiResponse<>(false, message, null);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    public static <T> ResponseEntity<ApiResponse<T>> notFound(String message) {
        ApiResponse<T> response = new ApiResponse<>(false, message, null);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    public static <T> ResponseEntity<ApiResponse<T>> unAuthorizedRequest(String message) {
        ApiResponse<T> response = new ApiResponse<>(false, message, null);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }


    public static <T> ResponseEntity<ApiResponse<T>> serverError(String message) {
        ApiResponse<T> response = new ApiResponse<>(false, message, null);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

}
