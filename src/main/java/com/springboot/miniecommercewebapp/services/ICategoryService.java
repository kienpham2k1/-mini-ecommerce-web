package com.springboot.miniecommercewebapp.services;

import com.springboot.miniecommercewebapp.models.CategoriesEntity;
import com.springboot.miniecommercewebapp.dto.response.SuccessResponse;
import org.springframework.http.ResponseEntity;

public interface ICategoryService {
    ResponseEntity<SuccessResponse> getAllCategories();

    ResponseEntity<SuccessResponse> getCategoyById(int categoryId);

    ResponseEntity<SuccessResponse> addCategory(CategoriesEntity newCategory);

    ResponseEntity<SuccessResponse> updateCategory(CategoriesEntity updateCategory, int categortId);

    ResponseEntity<SuccessResponse> deleteCategory(int categortId);
}
