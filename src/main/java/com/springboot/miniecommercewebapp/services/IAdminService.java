package com.springboot.miniecommercewebapp.services;

import com.springboot.miniecommercewebapp.models.ResponseObject;
import com.springboot.miniecommercewebapp.models.User;
import org.springframework.http.ResponseEntity;

public interface IAdminService {
    ResponseEntity<ResponseObject> login(String userId, String password);
    ResponseEntity<ResponseObject> register(User newUser);
}
