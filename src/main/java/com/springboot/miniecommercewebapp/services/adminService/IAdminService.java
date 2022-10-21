package com.springboot.miniecommercewebapp.services.adminService;

import com.springboot.miniecommercewebapp.exceptions.ResponseObject;
import com.springboot.miniecommercewebapp.models.User;
import org.springframework.http.ResponseEntity;

public interface IAdminService {
    ResponseEntity<ResponseObject> login(String userId, String password);
    ResponseEntity<ResponseObject> register(User newUser);
}
