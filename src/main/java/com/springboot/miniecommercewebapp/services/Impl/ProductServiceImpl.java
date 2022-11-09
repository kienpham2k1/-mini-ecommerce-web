package com.springboot.miniecommercewebapp.services.Impl;

import com.springboot.miniecommercewebapp.exceptions.ItemExistException;
import com.springboot.miniecommercewebapp.exceptions.NotFoundException;
import com.springboot.miniecommercewebapp.models.ProductsEntity;
import com.springboot.miniecommercewebapp.models.enums.EProductStatus;
import com.springboot.miniecommercewebapp.repositories.ProductRepository;
import com.springboot.miniecommercewebapp.services.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements IProductService {
    @Autowired
    private ProductRepository productRepository;

    /* Authorization with role:
     If user: Get products is only in-stock;
     If admin: Get all products
     */

    @Override
    public Page<ProductsEntity> getProductsWithPage(int page, int size, String sortable, String sort, String nameProduct, Integer categoryId) {
        Page<ProductsEntity> procductPage = null;
        Pageable pageProd = null;
        if (sort.equals("ASC")) {
            pageProd = PageRequest.of(page, size, Sort.by(sortable.trim()).ascending());
        } else if (sort.equals("DESC")) {
            pageProd = PageRequest.of(page, size, Sort.by(sortable.trim()).descending());
        }
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        procductPage = productRepository.findAll(pageProd);
        if (auth.getAuthorities().equals(List.of(new SimpleGrantedAuthority("ROLE_USER"))) || auth.getAuthorities().equals(List.of(new SimpleGrantedAuthority("ROLE_ANONYMOUS")))) {
            procductPage = productRepository.findAllByQuantityGreaterThanAndStatusEqualsIgnoreCase(pageProd);
            if (categoryId != null && nameProduct != null) {
                procductPage = productRepository.findAllByProductNameContainingIgnoreCaseAndCatagoryIdAndQuantityGreaterThanAndStatusEqualsIgnoreCase(nameProduct.trim(), categoryId, pageProd);
            } else if (nameProduct != null) {
                procductPage = productRepository.findAllByProductNameContainingIgnoreCaseAndQuantityGreaterThanAndStatusEqualsIgnoreCase(nameProduct.trim(), pageProd);
            } else if (categoryId != null) {
                procductPage = productRepository.findByCatagoryIdAndQuantityGreaterThanAndStatusEqualsIgnoreCase(categoryId, pageProd);
            }
        } else {
            if (categoryId != null && nameProduct != null) {
                procductPage = productRepository.findAllByProductNameContainingIgnoreCaseAndCatagoryId(nameProduct.trim(), categoryId, pageProd);
            } else if (nameProduct != null) {
                procductPage = productRepository.findAllByProductNameContainingIgnoreCase(nameProduct.trim(), pageProd);
            } else if (categoryId != null) {
                procductPage = productRepository.findByCatagoryId(categoryId, pageProd);
            }
        }
        if (procductPage.getContent().size() > 0) return procductPage;
        throw new NotFoundException("Failed to found product");
    }

    @Override
    public Optional<ProductsEntity> getProductById(int productId) {
        Optional<ProductsEntity> foundProductsEntity = productRepository.findById(productId);
        if (foundProductsEntity.isPresent()) return foundProductsEntity;
        throw new NotFoundException("Not found product");
    }

    /*
        Add new product:
            Check constrain: + Product name ?
                             + Price > 0
                             + Quantity > 0
                        ???  + Status must is in-stock
                             + Description
                             + Create date is the time local
        */
    @Override
    public ProductsEntity addNewProduct(ProductsEntity newProduct) {
        Optional<ProductsEntity> foundProducts = productRepository.findByProductName(newProduct.getProductName().trim());
        if (foundProducts.isEmpty()) {
            java.util.Date utilDate = new java.util.Date();
            java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
            newProduct.setCreateDate(sqlDate);
            newProduct.setStatus(EProductStatus.STOCKING);
            return productRepository.save(newProduct);
        }
        throw new ItemExistException("Product's name has already taken");
    }

    @Override
    public ProductsEntity updateProduct(int productId, ProductsEntity updateProduct) {
        Optional<ProductsEntity> foundProduct = productRepository.findById(productId);
        if (foundProduct.isPresent()) {
//        Check name
            Optional<ProductsEntity> foundName = productRepository.findByProductName(updateProduct.getProductName());
            if (foundName.isPresent() && foundName.get().getProductId() != productId)
                throw new ItemExistException("This name has already taken");
            updateProduct.setProductId(productId);
            String status = (updateProduct.getQuantity() != 0) ? "STOCKING" : "OUT_OF_STOCK";
            updateProduct.setStatus(EProductStatus.valueOf(status));
            return productRepository.save(updateProduct);
        }
        throw new NotFoundException("Failed! Product not exist to update");
    }

    @Override
    public ProductsEntity updateProduct(int id, int quantity) {
        Optional<ProductsEntity> availableProduct = productRepository.findById(id);
        if (availableProduct.isPresent()) {
            ProductsEntity newProduct = availableProduct.get();
            if ((newProduct.getQuantity() - quantity) == 0) newProduct.setStatus(EProductStatus.OUT_OF_STOCK);
            newProduct.setQuantity(newProduct.getQuantity() - quantity);
            return productRepository.save(newProduct);
        }
        throw new NotFoundException("Not found product");
//        return availableProduct;
    }

    @Override
    public boolean updateStatusProduct(int id, String newStatus) {
        Optional<ProductsEntity> updateProduct = productRepository.findById(id);
        if (updateProduct.isPresent()) {
            if (newStatus.equals("STOCKING") && updateProduct.get().getQuantity() == 0)
                throw new IllegalArgumentException("STOCKING product can not have quantity is 0");
            updateProduct.get().setStatus(EProductStatus.valueOf(newStatus));
            productRepository.save(updateProduct.get());
            return true;
        }
        throw new NotFoundException("Not found product.");
    }
}