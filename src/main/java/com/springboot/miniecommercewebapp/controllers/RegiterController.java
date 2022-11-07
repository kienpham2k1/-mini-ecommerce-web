package com.springboot.miniecommercewebapp.controllers;

import com.springboot.miniecommercewebapp.dto.request.UserRequestModel;
import com.springboot.miniecommercewebapp.models.UsersEntity;
import com.springboot.miniecommercewebapp.services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class RegiterController {
    @Autowired
    IUserService iUserService;

    @PostMapping("/register")
    ResponseEntity<?> register(@RequestBody @Valid UserRequestModel newUserRegister) {
        return new ResponseEntity<>(iUserService.register(newUserRegister), HttpStatus.OK);
    }
}
