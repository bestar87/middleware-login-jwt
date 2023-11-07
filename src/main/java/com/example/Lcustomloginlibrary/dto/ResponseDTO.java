package com.example.Lcustomloginlibrary.dto;


import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
/***
 * this is for the response data transfer object
 */
public class ResponseDTO {


    private String username;
    private String password;
    private String role;
    private String jwtToken;
    private Boolean status;

}
