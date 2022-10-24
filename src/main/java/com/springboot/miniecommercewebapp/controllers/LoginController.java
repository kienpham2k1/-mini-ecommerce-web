package com.springboot.miniecommercewebapp.controllers;

import com.springboot.miniecommercewebapp.dto.request.UserLoginRequestModel;
import com.springboot.miniecommercewebapp.dto.response.UserLoginResponseModel;
import com.springboot.miniecommercewebapp.services.JwtUserDetailsService;
import com.springboot.miniecommercewebapp.jwtUtils.TokenManager;
import com.springboot.miniecommercewebapp.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class LoginController {
    @Autowired
    private JwtUserDetailsService userDetailsService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private TokenManager tokenManager;
    @Autowired
    UserRepository userRepository;

    @PostMapping("/login")
    public ResponseEntity createToken(@RequestBody UserLoginRequestModel request) throws Exception {
        try {
            Authentication authentication =  authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsername()
                            , request.getPassword()
                    )
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
        final UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());
        final String jwtToken = tokenManager.generateJwtToken(userDetails);
        return ResponseEntity.ok(new UserLoginResponseModel(jwtToken));
    }
    @GetMapping("/login/context")
    String getContext () {

        return  "Hello World";
    }
}