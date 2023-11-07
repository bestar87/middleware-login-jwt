package com.example.Lcustomloginlibrary.dto;


import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
/***
 * this is for the request data transfer object
 */
public class RequestDTO {
    private String username;
    private String password;
    private String subject;
    private String audience;
    private String secretKey;

}
