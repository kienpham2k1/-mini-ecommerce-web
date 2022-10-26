package com.springboot.miniecommercewebapp.services.Impl;

import com.springboot.miniecommercewebapp.repositories.AdminRepository;
import com.springboot.miniecommercewebapp.services.IAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements IAdminService {
    @Autowired AdminRepository adminRepository;
}
