package com.springboot.miniecommercewebapp.services.adminService;

import com.springboot.miniecommercewebapp.models.AdminEntity;
import com.springboot.miniecommercewebapp.exceptions.ResponseObject;
import com.springboot.miniecommercewebapp.models.UserEntity;
import com.springboot.miniecommercewebapp.repositories.AdminRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AdminServiceImpl implements IAdminService {
    @Autowired AdminRepository adminRepository;
}
