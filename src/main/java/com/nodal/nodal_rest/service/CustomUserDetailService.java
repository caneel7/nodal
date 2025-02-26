package com.nodal.nodal_rest.service;

import com.nodal.nodal_rest.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomUserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public CustomUserDetailService(UserRepository repository)
    {
        this.userRepository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
    {
        com.nodal.nodal_rest.model.User user = userRepository.findById(Integer.parseInt(username)).orElseThrow(() -> new UsernameNotFoundException("Cannot Find User"));
        return new User(Integer.toString(user.getId()),user.getPassword(), List.of());
    }

    public UserDetails loadUserById(int id) throws UsernameNotFoundException
    {
        com.nodal.nodal_rest.model.User user = userRepository.findById(id).orElseThrow(()-> new UsernameNotFoundException("Cannot Find User"));
        return new User(Integer.toString(user.getId()),user.getPassword(),List.of());
    }
}
