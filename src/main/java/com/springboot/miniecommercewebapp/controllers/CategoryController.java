package com.springboot.miniecommercewebapp.controllers;

import com.springboot.miniecommercewebapp.dto.response.SuccessResponse;
import com.springboot.miniecommercewebapp.models.CategoriesEntity;
import com.springboot.miniecommercewebapp.services.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("category")
public class CategoryController {
    @Autowired
    ICategoryService iCategoryService;

    @GetMapping()
    ResponseEntity<?> getAllCategories() {
        return new ResponseEntity<>(
                new SuccessResponse(200, "Found success", iCategoryService.getAllCategories()), HttpStatus.OK);
    }

    @GetMapping("{categoryId}")
    ResponseEntity<?> getCategoryById(@PathVariable int categoryId) {
        return new ResponseEntity<>(
                new SuccessResponse(200, "Found success", iCategoryService.getCategoyById(categoryId)), HttpStatus.OK);
    }

    @PostMapping()
    ResponseEntity<?> addNewCategory(@Valid @RequestBody CategoriesEntity newCategory) {
        return new ResponseEntity<>(
                new SuccessResponse(201, "Add success", iCategoryService.addCategory(newCategory)), HttpStatus.CREATED);
    }

    @PutMapping("{categoryId}")
    ResponseEntity<?> updateCategory(@Valid @RequestBody CategoriesEntity updateCategory, @PathVariable int categoryId) {

        return new ResponseEntity<>(
                new SuccessResponse(200, "Update successs", iCategoryService.updateCategory(updateCategory, categoryId)), HttpStatus.OK);

    }

}
