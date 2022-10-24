package com.springboot.miniecommercewebapp.repositories;

import com.springboot.miniecommercewebapp.models.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, String> {
    Optional<UserEntity> findUserByUserIdAndPassword(String userId, String password);
    UserEntity findByUserId(String userId);
}
