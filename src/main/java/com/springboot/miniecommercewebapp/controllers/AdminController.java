package com.springboot.miniecommercewebapp.controllers;

import com.springboot.miniecommercewebapp.models.AdminsEntity;
import com.springboot.miniecommercewebapp.repositories.AdminRepository;
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
    List<AdminsEntity> getAllUsers(){
        return  adminRepository.findAll();
    }
}
