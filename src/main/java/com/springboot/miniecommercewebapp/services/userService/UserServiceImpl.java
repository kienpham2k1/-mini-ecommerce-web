package com.springboot.miniecommercewebapp.services.userService;

import com.springboot.miniecommercewebapp.exceptions.ResponseObject;
import com.springboot.miniecommercewebapp.models.User;
import com.springboot.miniecommercewebapp.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements IUserService {
    @Autowired
    UserRepository userRepository;

    public ResponseEntity<ResponseObject> login(String userId, String password) {
        Optional<User> loginUser = userRepository.findUserByUserIdAndPassword(userId, password);
        if (loginUser.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "Login successfully!", loginUser));
        } else
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("failed", "Login fail!", null));
    }

    public ResponseEntity<ResponseObject> register(User newUser) {
        Optional<User> foundUser = userRepository.findById(newUser.getUserId());
        if (foundUser.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "Insert successfully!", null));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
                    new ResponseObject("failed", "Fail, userId already taken!", null));
        }
    }

    public ResponseEntity<ResponseObject> updateUser(User newUser, String id) {
        Optional<User> updateUser = userRepository.findById(id)
                .map(user -> {
                    user.setFullName(newUser.getFullName());
                    user.setPassword(newUser.getPassword());
                    user.setAddress(newUser.getAddress());
                    user.setBirthday(newUser.getBirthday());
                    user.setPhone(newUser.getPhone());
                    user.setEmail(newUser.getEmail());
                    user.setStatus(newUser.isStatus());
                    return userRepository.save(user);
                });
        if (updateUser.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "Update successfully!", updateUser)
            );
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("failed", "User not found!", updateUser)
            );
        }
    }

    public ResponseEntity<ResponseObject> deleteUser(String userId) {
        Optional<User> deleteUser = userRepository.findById(userId)
                .map(user -> {
                    user.setStatus(false);
                    return userRepository.save(user);
                });
        if (deleteUser.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "Delete successfully!", "")
            );
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("failed", "User not found!", "")
            );
        }
    }
}
