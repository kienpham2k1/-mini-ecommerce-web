package com.springboot.miniecommercewebapp.controllers;

import com.springboot.miniecommercewebapp.exceptions.ResponseObject;
import com.springboot.miniecommercewebapp.models.User;
import com.springboot.miniecommercewebapp.services.userService.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/users")
public class UserController {
    @Autowired
    IUserService iUserService;

    @GetMapping("/login")
    ResponseEntity<ResponseObject> login(@RequestParam(value = "userId", required = true) String userId,
                                         @RequestParam(value = "password", required = true) String password) {
        return iUserService.login(userId, password);
    }

    @PostMapping("/register")
    ResponseEntity<ResponseObject> register(@RequestBody User newUser) {
        return iUserService.register(newUser);
    }

    @PutMapping("/update/{id}")
    ResponseEntity<ResponseObject> updateUser(@RequestBody User newUser, @PathVariable String id) {
        return iUserService.updateUser(newUser, id);
    }

    @DeleteMapping("/delete/{id}")
    ResponseEntity<ResponseObject> deleteUser(@PathVariable String id){
        return iUserService.deleteUser(id);
    }

}
