package com.springboot.miniecommercewebapp.repositories;

import com.springboot.miniecommercewebapp.models.UsersEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UsersEntity, String> {
    Optional<UsersEntity> findUserByUserIdAndPassword(String userId, String password);
    UsersEntity findByUserId(String userId);
}
