package com.springboot.miniecommercewebapp.repositories;

import com.springboot.miniecommercewebapp.models.CategoriesEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<CategoriesEntity, Integer> {
    Optional<CategoriesEntity> findByCatagoryName(String CategoryName);
}
