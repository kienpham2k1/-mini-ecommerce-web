package com.springboot.miniecommercewebapp.controllers;

import com.springboot.miniecommercewebapp.models.Role;
import com.springboot.miniecommercewebapp.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/roles")
public class RoleController {
    @Autowired
    RoleRepository roleRepository;
    @GetMapping("")
    List<Role> getAllRoles(){
        return  roleRepository.findAll();
    }
}
