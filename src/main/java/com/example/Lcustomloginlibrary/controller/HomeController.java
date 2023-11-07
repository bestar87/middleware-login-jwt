package com.example.Lcustomloginlibrary.controller;

import com.example.Lcustomloginlibrary.dto.RequestDTO;
import com.example.Lcustomloginlibrary.dto.ResponseDTO;
import com.example.Lcustomloginlibrary.service.LoginService;
import com.example.Lcustomloginlibrary.util.JwtToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;


@EnableWebSecurity
@RestController
public class HomeController {

    @Autowired
    private LoginService loginService;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtToken jwtToken;

    @Autowired
    @Lazy
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/home")
    public String home(){
        return "Hi,here!";
    }

    @PostMapping("/authenticate")
    public ResponseDTO postAuthenticate(@RequestBody RequestDTO dto){
       try {
           System.out.println("This is post authenticate");
           UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPassword());
           Authentication authentication = authenticationManager.authenticate(token);
           System.out.println("Is authenticate: " + authentication.isAuthenticated());
           System.out.println("Principal: " + authentication.getPrincipal());
           System.out.println("Credential: " + authentication.getCredentials());
       } catch (Exception e){
           System.out.println("Cannot authenticate!");
           throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Can not authenticate", e);
       }


        UserDetails userDetails = userDetailsService.loadUserByUsername(dto.getUsername());
        System.out.println("userDetails: " + userDetails.getUsername() + ", " + userDetails.getPassword());

        String role = loginService.getAccountRole(dto.getUsername());
        String token = jwtToken.generateToken(dto.getSubject(),dto.getUsername(), dto.getSecretKey(), role, dto.getAudience());
        return new ResponseDTO(dto.getUsername(),dto.getPassword(), role, token, true);
    }





}
