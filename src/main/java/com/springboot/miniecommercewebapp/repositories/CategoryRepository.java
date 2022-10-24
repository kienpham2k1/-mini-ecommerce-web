package com.springboot.miniecommercewebapp.repositories;

import com.springboot.miniecommercewebapp.models.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<CategoryEntity, Integer> {
    Optional<CategoryEntity> findByCatagoryName(String CategoryName);
}
