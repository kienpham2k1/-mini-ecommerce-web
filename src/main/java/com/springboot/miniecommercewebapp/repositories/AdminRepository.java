package com.springboot.miniecommercewebapp.repositories;

import com.springboot.miniecommercewebapp.models.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin, String> {
}
