package com.springboot.miniecommercewebapp.repositories;

import com.springboot.miniecommercewebapp.models.UsersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UsersEntity, String> {
    Optional<UsersEntity> findUserByUserIdAndPassword(String userId, String password);
    Optional<UsersEntity> findByUserId(String userId);

    Optional<UsersEntity> findByEmail(String email);
}
