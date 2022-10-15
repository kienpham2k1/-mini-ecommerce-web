package com.springboot.miniecommercewebapp.repositories;

import com.springboot.miniecommercewebapp.models.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Users, String> {
}
