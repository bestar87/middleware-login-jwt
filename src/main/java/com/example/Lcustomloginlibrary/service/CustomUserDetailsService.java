package com.example.Lcustomloginlibrary.service;

import com.example.Lcustomloginlibrary.ApplicationUserDetails;
import com.example.Lcustomloginlibrary.entity.UserAccount;
import com.example.Lcustomloginlibrary.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserAccount> entityUserFromRepo = userRepository.findByUsername(username);
        UserAccount user = entityUserFromRepo.get();
        return new ApplicationUserDetails(user);
    }

}
