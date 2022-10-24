package com.springboot.miniecommercewebapp.services.categoryService;

import com.springboot.miniecommercewebapp.models.CategoryEntity;
import com.springboot.miniecommercewebapp.exceptions.ResponseObject;
import com.springboot.miniecommercewebapp.repositories.CategoryRepository;
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
    public ResponseEntity<ResponseObject> getAllCategories() {
        List<CategoryEntity> categoryList = categoryRepository.findAll();
        if (categoryList.size() > 0) return
                ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok", "OK", categoryList));
        else return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject("failed", "Not Found", ""));
    }

    @Override
    public ResponseEntity<ResponseObject> getCategoyById(int categoryId) {
        Optional<CategoryEntity> category = categoryRepository.findById(categoryId);
        if (category.isPresent())
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok", "OK", category));
        else
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject("failed", "Not found category", ""));
    }

    @Override
    public ResponseEntity<ResponseObject> addCategory(CategoryEntity newCategory) {
        Optional<CategoryEntity> foundCategory = categoryRepository.findByCatagoryName(newCategory.getCatagoryName());
        if (foundCategory.isEmpty())
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok", "Insert successfully", categoryRepository.save(newCategory)));
        else
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(new ResponseObject("failed", "Category name already exist!", ""));
    }

    @Override
    public ResponseEntity<ResponseObject> updateCategory(CategoryEntity updateCategory, int categoryId) {
        Optional<CategoryEntity> foundCategory = categoryRepository.findById(categoryId)
                .map(category -> {
                    category.setCatagoryName(updateCategory.getCatagoryName());
                    category.setDescription(updateCategory.getDescription());
                    return categoryRepository.save(category);
                });
        if (foundCategory.isPresent())
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok", "Update successfully", foundCategory));
        else
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject("failed", "Not found Category", ""));
    }

    @Override
    public ResponseEntity<ResponseObject> deleteCategory(int categortId) {
        return null;
    }
}
