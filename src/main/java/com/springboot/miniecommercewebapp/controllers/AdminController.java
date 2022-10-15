package com.springboot.miniecommercewebapp.controllers;

import com.springboot.miniecommercewebapp.models.Admins;
import com.springboot.miniecommercewebapp.models.Users;
import com.springboot.miniecommercewebapp.repositories.AdminRepository;
import com.springboot.miniecommercewebapp.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/admins")
public class AdminController {
    @Autowired
    AdminRepository adminRepository;
    @GetMapping("")
    List<Admins> getAllUsers(){
        return  adminRepository.findAll();
    }
}
