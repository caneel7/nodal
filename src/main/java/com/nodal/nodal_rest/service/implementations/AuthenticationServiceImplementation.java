package com.nodal.nodal_rest.service.implementations;

import com.nodal.nodal_rest.dto.ApiResponse;
import com.nodal.nodal_rest.dto.request.UserRequestDTO;
import com.nodal.nodal_rest.libs.ResponseEntityBuilder;
import com.nodal.nodal_rest.model.User;
import com.nodal.nodal_rest.repository.UserRepository;
import com.nodal.nodal_rest.service.AuthenticationService;
import com.nodal.nodal_rest.service.CustomUserDetailService;
import com.nodal.nodal_rest.service.JWTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthenticationServiceImplementation implements AuthenticationService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JWTService jwtService;
    @Autowired
    private CustomUserDetailService userDetailService;

    @Override
    public ResponseEntity<ApiResponse<Boolean>> register(UserRequestDTO user) {

        if(user.getFirstName() == null || user.getLastName() == null)
            return ResponseEntityBuilder.badRequest("Please Provide First Name And Last Name");

        if(user.getEmail() == null || user.getPassword() == null)
            return ResponseEntityBuilder.badRequest("Please Provide Email And Password");

        try{
            User foundUser = userRepository.findByEmail(user.getEmail());

            if(foundUser != null)
                return ResponseEntityBuilder.badRequest("User Already Exist");

            User newUser = User.builder()
                    .firstName(user.getFirstName())
                    .lastName(user.getLastName())
                    .email(user.getEmail())
                    .password(passwordEncoder.encode(user.getPassword()))
            .build();

            userRepository.save(newUser);

            return ResponseEntityBuilder.success(true,"Registered Successfully");

        }catch (Exception e){
            return ResponseEntityBuilder.serverError(e.getMessage());
        }
    }

    @Override
    public ResponseEntity<ApiResponse<User>> login(UserRequestDTO user) {
        if(user.getEmail() == null && user.getPassword() == null)
            return ResponseEntityBuilder.badRequest("Please Provide Email And Password");

        try{

            User foundUser = userRepository.findByEmail(user.getEmail());

            if(foundUser == null)
                return ResponseEntityBuilder.notFound("Cannot Find User");

            boolean passwordMatch = passwordEncoder.matches(user.getPassword(),foundUser.getPassword());

            if(!passwordMatch)
                return ResponseEntityBuilder.unAuthorizedRequest("Invalid Credentials");

            if(!foundUser.isVerified())
                return ResponseEntityBuilder.badRequest("Please Verify Email Before Logging In");

            String token = jwtService.generateToken(userDetailService.loadUserById(foundUser.getId()));

            foundUser.setToken(token);

            return ResponseEntityBuilder.success(foundUser,"Logged In Successfully");
        }catch (Exception e){
            return ResponseEntityBuilder.serverError(e.getMessage());
        }
    }
}
