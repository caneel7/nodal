package com.nodal.nodal_rest.dto.request;

import lombok.Data;

import java.util.Optional;

@Data
public class UserRequestDTO {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Optional<String> newPassword;
}
