package com.nodal.nodal_rest.service;

import com.nodal.nodal_rest.dto.ApiResponse;
import com.nodal.nodal_rest.dto.request.UserRequestDTO;
import com.nodal.nodal_rest.model.User;
import org.springframework.http.ResponseEntity;

public interface AuthenticationService {

    ResponseEntity<ApiResponse<Boolean>> register(UserRequestDTO user);

    ResponseEntity<ApiResponse<User>> login(UserRequestDTO user);
}
