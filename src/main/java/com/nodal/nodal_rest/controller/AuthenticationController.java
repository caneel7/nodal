package com.nodal.nodal_rest.controller;

import com.nodal.nodal_rest.dto.ApiResponse;
import com.nodal.nodal_rest.dto.request.UserRequestDTO;
import com.nodal.nodal_rest.model.User;
import com.nodal.nodal_rest.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/auth", produces = "application/json")
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;


    @PostMapping(value = "/register")
    public ResponseEntity<ApiResponse<Boolean>> register(@RequestBody UserRequestDTO body)
    {
        return authenticationService.register(body);
    }

    @PostMapping(value = "/login")
    public ResponseEntity<ApiResponse<User>> login(@RequestBody UserRequestDTO body)
    {
        return authenticationService.login(body);
    }
}
