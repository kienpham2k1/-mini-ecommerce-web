package com.springboot.miniecommercewebapp.services.Impl;

import com.springboot.miniecommercewebapp.dto.response.SuccessResponse;
import com.springboot.miniecommercewebapp.models.UsersEntity;
import com.springboot.miniecommercewebapp.repositories.UserRepository;
import com.springboot.miniecommercewebapp.services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements IUserService {
    @Autowired
    UserRepository userRepository;

    @Override
    public Optional<UsersEntity> getUser(String userId, String password) {
        return userRepository.findUserByUserIdAndPassword(userId, password);
    }

    public ResponseEntity<SuccessResponse> register(UsersEntity newUser) {
        Optional<UsersEntity> foundUser = userRepository.findById(newUser.getUserId());
        if (foundUser.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new SuccessResponse(200, "Insert successfully!", null));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
                    new SuccessResponse(400, "Fail, userId already taken!", null));
        }
    }

    public ResponseEntity<SuccessResponse> updateUser(UsersEntity newUser, String id) {
        Optional<UsersEntity> updateUser = userRepository.findById(id)
                .map(user -> {
                    user.setFullName(newUser.getFullName());
                    user.setPassword(newUser.getPassword());
                    user.setAddress(newUser.getAddress());
                    user.setBirthday(newUser.getBirthday());
                    user.setPhone(newUser.getPhone());
                    user.setEmail(newUser.getEmail());
                    user.setStatus(newUser.getStatus());
                    return userRepository.save(user);
                });
        if (updateUser.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new SuccessResponse(200, "Update successfully!", updateUser)
            );
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new SuccessResponse(400, "User not found!", updateUser)
            );
        }
    }

    public ResponseEntity<SuccessResponse> deleteUser(String userId) {
        Optional<UsersEntity> deleteUser = userRepository.findById(userId)
                .map(user -> {
                    //user.setStatus(0);
                    return userRepository.save(user);
                });
        if (deleteUser.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new SuccessResponse(200, "Delete successfully!", "")
            );
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new SuccessResponse(400, "User not found!", "")
            );
        }
    }
}
