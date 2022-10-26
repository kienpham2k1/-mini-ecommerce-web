package com.springboot.miniecommercewebapp.repositories;

import com.springboot.miniecommercewebapp.models.AdminsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<AdminsEntity, String> {
}
