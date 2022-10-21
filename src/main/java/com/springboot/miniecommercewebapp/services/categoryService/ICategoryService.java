package com.springboot.miniecommercewebapp.services.categoryService;

import com.springboot.miniecommercewebapp.models.Category;
import com.springboot.miniecommercewebapp.exceptions.ResponseObject;
import org.springframework.http.ResponseEntity;

public interface ICategoryService {
    ResponseEntity<ResponseObject> getAllCategories();

    ResponseEntity<ResponseObject> getCategoyById(int categoryId);

    ResponseEntity<ResponseObject> addCategory(Category newCategory);

    ResponseEntity<ResponseObject> updateCategory(Category updateCategory, int categortId);

    ResponseEntity<ResponseObject> deleteCategory(int categortId);
}
