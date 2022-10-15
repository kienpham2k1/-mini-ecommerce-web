package com.springboot.miniecommercewebapp.repositories;

import com.springboot.miniecommercewebapp.models.Admins;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admins, String> {
}
