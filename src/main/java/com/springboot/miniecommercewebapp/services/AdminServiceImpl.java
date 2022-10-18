package com.springboot.miniecommercewebapp.services;

import com.springboot.miniecommercewebapp.models.Admin;
import com.springboot.miniecommercewebapp.models.ResponseObject;
import com.springboot.miniecommercewebapp.models.User;
import com.springboot.miniecommercewebapp.repositories.AdminRepository;
import com.springboot.miniecommercewebapp.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class AdminServiceImpl implements IAdminService{
    @Autowired AdminRepository adminRepository;
    public ResponseEntity<ResponseObject> login(String userId, String password) {
        Optional<Admin> loginUser = adminRepository.findByUserIdAndPassword(userId, password);
        if (loginUser.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "Login successfully!", loginUser));
        } else
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("failed", "Login fail!", null));
    }

    public ResponseEntity<ResponseObject> register(User newUser) {
        Optional<Admin> foundUser = adminRepository.findById(newUser.getUserId());
        if (foundUser.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "Insert successfully!", null));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
                    new ResponseObject("failed", "Fail, userId already taken!", null));
        }
    }

}
