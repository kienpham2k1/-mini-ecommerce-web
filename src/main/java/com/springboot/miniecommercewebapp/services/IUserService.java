package com.springboot.miniecommercewebapp.services;

import com.springboot.miniecommercewebapp.dto.request.UserRequestModel;
import com.springboot.miniecommercewebapp.models.UsersEntity;

import java.util.List;
import java.util.Optional;

public interface IUserService {
    List<UsersEntity> getAllUser();
    Optional<UsersEntity> getUser(String userId);
    UsersEntity register(UserRequestModel newUser);
    UsersEntity updateUser(String id, UserRequestModel newUser);
    boolean deleteUser(String userId);

}
