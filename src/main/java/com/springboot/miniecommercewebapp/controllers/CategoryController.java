package com.springboot.miniecommercewebapp.controllers;

import com.springboot.miniecommercewebapp.models.CategoryEntity;
import com.springboot.miniecommercewebapp.exceptions.ResponseObject;
import com.springboot.miniecommercewebapp.services.categoryService.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("category")
public class CategoryController {
    @Autowired
    ICategoryService iCategoryService;

    @GetMapping()
    ResponseEntity<ResponseObject> getAllCategories() {
        return iCategoryService.getAllCategories();
    }

    @GetMapping("{categoryId}")
    ResponseEntity<ResponseObject> getCategoryById(@PathVariable int categoryId) {
        return iCategoryService.getCategoyById(categoryId);
    }

    @PostMapping()
    ResponseEntity<ResponseObject> addNewCategory(@RequestBody CategoryEntity newCategory) {
        return iCategoryService.addCategory(newCategory);
    }

    @PutMapping("{categoryId}")
    ResponseEntity<ResponseObject> updateCategory(@RequestBody CategoryEntity updateCategory, @PathVariable int categoryId) {
        return iCategoryService.updateCategory(updateCategory, categoryId);
    }

}
