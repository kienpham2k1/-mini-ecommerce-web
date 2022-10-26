package com.springboot.miniecommercewebapp.services;

import com.springboot.miniecommercewebapp.models.CategoriesEntity;
import com.springboot.miniecommercewebapp.dto.response.SuccessResponse;
import org.springframework.expression.spel.ast.OpAnd;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface ICategoryService {
    List<CategoriesEntity> getAllCategories();

    Optional<CategoriesEntity> getCategoyById(int categoryId);

    CategoriesEntity addCategory(CategoriesEntity newCategory);

    CategoriesEntity updateCategory(CategoriesEntity updateCategory, int categortId);

    boolean deleteCategory(int categortId);
}
