package com.nodal.nodal_rest.controller;

import com.nodal.nodal_rest.dto.ApiResponse;
import com.nodal.nodal_rest.dto.request.UserRequestDTO;
import com.nodal.nodal_rest.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/user",produces = "application/json")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/change-password")
    public ResponseEntity<ApiResponse<Boolean>> updatePassword(@RequestBody UserRequestDTO body)
    {
        return userService.updatePassword(body);
    }
}
