package com.springboot.miniecommercewebapp.repositories;

import com.springboot.miniecommercewebapp.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
}
