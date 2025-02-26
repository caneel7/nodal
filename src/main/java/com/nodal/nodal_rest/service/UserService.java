package com.nodal.nodal_rest.service;

import com.nodal.nodal_rest.dto.ApiResponse;
import com.nodal.nodal_rest.dto.request.UserRequestDTO;
import org.springframework.http.ResponseEntity;

public interface UserService {

    ResponseEntity<ApiResponse<Boolean>> updatePassword(UserRequestDTO body);
}
