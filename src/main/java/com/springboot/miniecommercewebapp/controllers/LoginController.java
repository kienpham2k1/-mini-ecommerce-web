package com.springboot.miniecommercewebapp.controllers;

import com.springboot.miniecommercewebapp.dto.request.UserLoginRequestModel;
import com.springboot.miniecommercewebapp.dto.response.SuccessResponse;
import com.springboot.miniecommercewebapp.dto.response.UserLoginResponseModel;
import com.springboot.miniecommercewebapp.jwtUtils.TokenManager;
import com.springboot.miniecommercewebapp.repositories.UserRepository;
import com.springboot.miniecommercewebapp.services.Impl.JwtUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
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
    public ResponseEntity<?> createToken(@RequestBody UserLoginRequestModel request) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        final UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());
        final String jwtToken = tokenManager.generateJwtToken(userDetails);
        return new ResponseEntity<>(new SuccessResponse(200, "Login success", new UserLoginResponseModel(userDetails.getUsername(), userDetails.getAuthorities().toString(), jwtToken)), HttpStatus.OK);
    }
}