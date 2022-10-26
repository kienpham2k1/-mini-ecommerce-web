package com.springboot.miniecommercewebapp.services.Impl;

import com.springboot.miniecommercewebapp.repositories.CategoryRepository;
import com.springboot.miniecommercewebapp.services.ICategoryService;
import com.springboot.miniecommercewebapp.models.CategoriesEntity;
import com.springboot.miniecommercewebapp.dto.response.SuccessResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements ICategoryService {
    @Autowired
    CategoryRepository categoryRepository;

    @Override
    public ResponseEntity<SuccessResponse> getAllCategories() {
        List<CategoriesEntity> categoryList = categoryRepository.findAll();
        if (categoryList.size() > 0) return
                ResponseEntity.status(HttpStatus.OK).body(new SuccessResponse("ok", "OK", categoryList));
        else return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new SuccessResponse("failed", "Not Found", ""));
    }

    @Override
    public ResponseEntity<SuccessResponse> getCategoyById(int categoryId) {
        Optional<CategoriesEntity> category = categoryRepository.findById(categoryId);
        if (category.isPresent())
            return ResponseEntity.status(HttpStatus.OK).body(new SuccessResponse("ok", "OK", category));
        else
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new SuccessResponse("failed", "Not found category", ""));
    }

    @Override
    public ResponseEntity<SuccessResponse> addCategory(CategoriesEntity newCategory) {
        Optional<CategoriesEntity> foundCategory = categoryRepository.findByCatagoryName(newCategory.getCatagoryName());
        if (foundCategory.isEmpty())
            return ResponseEntity.status(HttpStatus.OK).body(new SuccessResponse("ok", "Insert successfully", categoryRepository.save(newCategory)));
        else
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(new SuccessResponse("failed", "Category name already exist!", ""));
    }

    @Override
    public ResponseEntity<SuccessResponse> updateCategory(CategoriesEntity updateCategory, int categoryId) {
        Optional<CategoriesEntity> foundCategory = categoryRepository.findById(categoryId)
                .map(category -> {
                    category.setCatagoryName(updateCategory.getCatagoryName());
                    //category.setDescription(updateCategory.getDescription());
                    return categoryRepository.save(category);
                });
        if (foundCategory.isPresent())
            return ResponseEntity.status(HttpStatus.OK).body(new SuccessResponse("ok", "Update successfully", foundCategory));
        else
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new SuccessResponse("failed", "Not found Category", ""));
    }

    @Override
    public ResponseEntity<SuccessResponse> deleteCategory(int categortId) {
        return null;
    }
}
