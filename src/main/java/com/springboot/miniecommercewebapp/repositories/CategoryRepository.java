package com.springboot.miniecommercewebapp.repositories;

import com.springboot.miniecommercewebapp.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
    Optional<Category> findByCatagoryName(String CategoryName);
}
