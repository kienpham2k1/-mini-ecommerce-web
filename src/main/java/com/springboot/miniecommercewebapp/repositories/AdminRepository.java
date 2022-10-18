package com.springboot.miniecommercewebapp.repositories;

import com.springboot.miniecommercewebapp.models.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin, String> {
    Optional<Admin> findByUserIdAndPassword(String userID, String password);
}
