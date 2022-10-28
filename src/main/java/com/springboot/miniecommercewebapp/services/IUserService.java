package com.springboot.miniecommercewebapp.services;

import com.springboot.miniecommercewebapp.dto.response.SuccessResponse;
import com.springboot.miniecommercewebapp.models.UsersEntity;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

public interface IUserService {
    Optional<UsersEntity> getUser(String userId, String password);
    ResponseEntity<SuccessResponse> register(UsersEntity newUser);
    ResponseEntity<SuccessResponse> updateUser(UsersEntity newUse, String id);
    ResponseEntity<SuccessResponse> deleteUser(String userId);

}
