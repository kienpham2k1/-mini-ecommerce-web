package com.springboot.miniecommercewebapp.services.userService;

import com.springboot.miniecommercewebapp.exceptions.ResponseObject;
import com.springboot.miniecommercewebapp.models.UserEntity;
import org.springframework.http.ResponseEntity;

public interface IUserService {
    ResponseEntity<ResponseObject> register(UserEntity newUser);
    ResponseEntity<ResponseObject> updateUser(UserEntity newUse, String id);
    ResponseEntity<ResponseObject> deleteUser(String userId);

}
