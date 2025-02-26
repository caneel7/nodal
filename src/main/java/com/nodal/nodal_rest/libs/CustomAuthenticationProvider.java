package com.nodal.nodal_rest.libs;

import com.nodal.nodal_rest.model.User;
import com.nodal.nodal_rest.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;



@Component
public class CustomAuthenticationProvider {

    @Autowired
    private UserRepository userRepository;

    public User getUser()
    {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if(auth == null)
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);

        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        String username = userDetails.getUsername();

        int id = 0;

        try{
            id = Integer.parseInt(username);
        }catch (NumberFormatException e){
            id = -1;
        }

        if(id == 0 || id == -1)
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);

        return userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED));
    }
}
