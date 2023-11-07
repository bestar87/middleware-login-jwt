package com.example.Lcustomloginlibrary.util;

import com.example.Lcustomloginlibrary.service.CustomUserDetailsService;
import com.example.Lcustomloginlibrary.service.LoginService;
import com.example.Lcustomloginlibrary.service.LoginServiceImpl;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private JwtToken jwtToken;

    /*** This is for creating the filter everytime the user is trying to login
     *
     * @param request
     * @param response
     * @param filterChain
     * @throws ServletException
     * @throws IOException
     */

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        /*** About the header
         *
         * the 'header' variable is defined to get the content from Authorization Header
         */
        String header = request.getHeader("Authorization");
        String username = null;
        String token = null;

        if(header != null) {
            token = header.replace("Bearer ", "");
            try {
                username = jwtToken.getUsername(token);
            } catch (ExpiredJwtException e) {
                e.printStackTrace();
            }
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(username != null && authentication == null){
            UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);
            if(jwtToken.validateToken(token, userDetails).equals(true)){
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }
        filterChain.doFilter(request, response);

    }
}
