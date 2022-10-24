package com.springboot.miniecommercewebapp.repositories;

import com.springboot.miniecommercewebapp.models.AdminEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<AdminEntity, String> {
}
