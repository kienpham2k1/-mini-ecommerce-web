package com.springboot.miniecommercewebapp.services.Impl;

import com.springboot.miniecommercewebapp.exceptions.ItemExistException;
import com.springboot.miniecommercewebapp.exceptions.NotFoundException;
import com.springboot.miniecommercewebapp.models.CategoriesEntity;
import com.springboot.miniecommercewebapp.repositories.CategoryRepository;
import com.springboot.miniecommercewebapp.services.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements ICategoryService {
    @Autowired
    CategoryRepository categoryRepository;

    @Override
    public List<CategoriesEntity> getAllCategories() {
        List<CategoriesEntity> categoryList = categoryRepository.findAll();
        if (categoryList.size() > 0) return categoryList;
        throw new NotFoundException("Not found");
    }

    @Override
    public Optional<CategoriesEntity> getCategoyById(int categoryId) {
        Optional<CategoriesEntity> category = categoryRepository.findById(categoryId);
        if (category.isPresent())
            return category;
        throw new NotFoundException("Not found");
    }

    @Override
    public CategoriesEntity addCategory(CategoriesEntity newCategory) {
        Optional<CategoriesEntity> foundCategory = categoryRepository.findByCatagoryName(newCategory.getCatagoryName());
        if (foundCategory.isEmpty())
            return categoryRepository.save(newCategory);
        throw new ItemExistException("Name has already taken");
    }

    @Override
    public CategoriesEntity updateCategory(CategoriesEntity updateCategory, int categoryId) {
        Optional<CategoriesEntity> foundCategory = categoryRepository.findById(categoryId);
        if (foundCategory.isPresent()) {
            updateCategory.setCatagoryId(categoryId);
            return categoryRepository.save(updateCategory);
        }
        throw new NotFoundException("Not found to update");
    }

    @Override
    public boolean deleteCategory(int categortId) {
        return false;
    }
}
