package com.main.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import com.main.model.User;
import com.main.repository.UserRepository;

@Component
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User userObj = userRepository.findByUserId(username);
        System.out.println("loadUserByUsername: " + username);
        if (userObj == null) {
            throw new UsernameNotFoundException("User name not found!");
        } else {
            return userObj;
        }
    }

}
