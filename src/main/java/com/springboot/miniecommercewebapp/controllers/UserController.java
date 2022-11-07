package com.springboot.miniecommercewebapp.controllers;

import com.springboot.miniecommercewebapp.dto.request.UserRequestModel;
import com.springboot.miniecommercewebapp.dto.response.SuccessResponse;
import com.springboot.miniecommercewebapp.models.UsersEntity;
import com.springboot.miniecommercewebapp.services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/users")
public class UserController {
    @Autowired
    IUserService iUserService;

    @GetMapping()
    ResponseEntity<?> getAllUsers(){
        return  new ResponseEntity<>(iUserService.getAllUser(), HttpStatus.OK);
    }
    @GetMapping("{userId}")
    ResponseEntity<?> getUser(@PathVariable String userId) {

        return new ResponseEntity<>(iUserService.getUser(userId), HttpStatus.OK);
    }

    @PostMapping("")
    ResponseEntity<?> register(@RequestBody UserRequestModel newUser) {
        return new ResponseEntity<>(iUserService.register(newUser), HttpStatus.OK);
    }

    @PutMapping("{id}")
    ResponseEntity<?> updateUser( @PathVariable String id, @RequestBody @Valid UserRequestModel newUser) {
        return new ResponseEntity<SuccessResponse>(new SuccessResponse( 200, "Update Success",iUserService.updateUser(id, newUser)),
                HttpStatus.OK);
    }

    @DeleteMapping()
    ResponseEntity<?> deleteUser(@RequestParam(name = "userId") String userId) {
        return new ResponseEntity<>(iUserService.deleteUser(userId), HttpStatus.OK);
    }

}
