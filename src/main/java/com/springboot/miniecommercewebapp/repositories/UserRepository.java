package com.springboot.miniecommercewebapp.repositories;

import com.springboot.miniecommercewebapp.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findUserByUserIdAndPassword(String userId, String password);
}
