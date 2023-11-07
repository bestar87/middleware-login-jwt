package com.example.Lcustomloginlibrary.service;

import com.example.Lcustomloginlibrary.ApplicationUserDetails;
import com.example.Lcustomloginlibrary.entity.UserAccount;
import com.example.Lcustomloginlibrary.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Qualifier("loginService")
public class LoginServiceImpl implements LoginService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public String getAccountRole(String username) {
        Optional<UserAccount> entityUserFromRepo = userRepository.findByUsername(username);
        UserAccount user = entityUserFromRepo.get();
        return user.getRole();
    }

}
