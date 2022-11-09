package com.springboot.miniecommercewebapp.repositories;

import com.springboot.miniecommercewebapp.models.AdminsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminRepository extends JpaRepository<AdminsEntity, String> {
}
