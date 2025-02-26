package com.nodal.nodal_rest.service.implementations;

import com.nodal.nodal_rest.dto.ApiResponse;
import com.nodal.nodal_rest.dto.request.UserRequestDTO;
import com.nodal.nodal_rest.libs.CustomAuthenticationProvider;
import com.nodal.nodal_rest.libs.ResponseEntityBuilder;
import com.nodal.nodal_rest.model.User;
import com.nodal.nodal_rest.repository.UserRepository;
import com.nodal.nodal_rest.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImplementation implements UserService {

    @Autowired
    private CustomAuthenticationProvider authenticationProvider;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;

    @Override
    public ResponseEntity<ApiResponse<Boolean>> updatePassword(UserRequestDTO body) {
        User user = authenticationProvider.getUser();

        if(body.getPassword() == null && body.getNewPassword() == null)
            return ResponseEntityBuilder.badRequest("Please Provide Old And New Password");
        try{
            boolean match = passwordEncoder.matches(body.getPassword(), user.getPassword());

            if(!match)
                return ResponseEntityBuilder.badRequest("Invalid Old Password");

            String newPassword = passwordEncoder.encode(body.getNewPassword().get());

            user.setPassword(newPassword);

            userRepository.save(user);

            return ResponseEntityBuilder.success(true,"Password Updated");
        }catch (Exception e){
            return ResponseEntityBuilder.serverError(e.getMessage());
        }
    }
}
