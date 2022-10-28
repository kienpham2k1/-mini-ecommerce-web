package com.springboot.miniecommercewebapp.controllers;

import com.springboot.miniecommercewebapp.dto.response.SuccessResponse;
import com.springboot.miniecommercewebapp.models.UsersEntity;
import com.springboot.miniecommercewebapp.services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/users")
public class UserController {
    @Autowired
    IUserService iUserService;

    @GetMapping("")
    ResponseEntity<?> getUser(@RequestParam(name = "userId")String userId, @RequestParam(name = "password") String password) {
            return new ResponseEntity<>(iUserService.getUser(userId, password), HttpStatus.OK);
    }

    @PostMapping("")
    ResponseEntity<SuccessResponse> register(@RequestBody UsersEntity newUser) {
        return iUserService.register(newUser);
    }

    @PutMapping("{id}")
    ResponseEntity<SuccessResponse> updateUser(@RequestBody UsersEntity newUser, @PathVariable String id) {
        return iUserService.updateUser(newUser, id);
    }

    @DeleteMapping("{id}")
    ResponseEntity<SuccessResponse> deleteUser(@PathVariable String id) {
        return iUserService.deleteUser(id);
    }

}
