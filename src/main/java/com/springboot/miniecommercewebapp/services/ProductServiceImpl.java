package com.springboot.miniecommercewebapp.services;

import com.springboot.miniecommercewebapp.models.Product;
import com.springboot.miniecommercewebapp.models.ResponseObject;
import com.springboot.miniecommercewebapp.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements IProductService {
    @Autowired
    private ProductRepository productRepository;

    @Override
    public ResponseEntity<ResponseObject> getAllProducts() {
        List<Product> listProducts = productRepository.findAll();
        if (!listProducts.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "Product found", listProducts)
            );
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("failed", "Product not found", null)
            );
        }
    }

    @Override
    public ResponseEntity<ResponseObject> getProductsWithPage(int page, int size, String sortable, String sort) {
        Pageable pageable = null;
        if (sort.equals("ASC")) {
            pageable = PageRequest.of(page, size, Sort.by(sortable.trim()).ascending());
        } else if (sort.equals("DESC")) {
            pageable = PageRequest.of(page, size, Sort.by(sortable.trim()).ascending());
        }
        Page<Product> pg = productRepository.findProducts(pageable);
        if (!pg.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "Product found", pg)
            );
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("failed", "Product not found", null)
            );
        }
    }

    @Override
    public ResponseEntity<ResponseObject> getProductsByCategoryId(int categoryId) {
        List<Product> foundProducts = productRepository.findByCatagoryId(categoryId);
        if (!foundProducts.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "Product found", foundProducts)
            );
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("failed", "Product not found", null)
            );
        }
    }

    @Override
    public ResponseEntity<ResponseObject> getProductDetailById(int id) {
        Optional<Product> foundProduct = productRepository.findById(id);
        if (foundProduct.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "Product found", foundProduct)
            );
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("failed", "Product not found", foundProduct)
            );
        }
    }

    @Override
    public ResponseEntity<ResponseObject> addNewProduct(Product newProduct) {
        List<Product> foundProducts = productRepository.findByProductName(newProduct.getProductName().trim());
        if (foundProducts.size() > 0)
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
                    new ResponseObject("failed", "Insert not successfully, product name already taken!", null)
            );
        else
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "Insert successfully!", productRepository.save(newProduct))
            );
    }

    @Override
    public ResponseEntity<ResponseObject> updateProduct(Product newProduct, int id) {
        Optional<Product> updateProduct = productRepository.findById(id)
                .map(product -> {
                    product.setProductName(newProduct.getProductName());
                    product.setImage(newProduct.getImage());
                    product.setPrice(newProduct.getPrice());
                    product.setQuantity(newProduct.getQuantity());
                    product.setCatagoryId(newProduct.getCatagoryId());
                    product.setStatus(newProduct.isStatus());
                    return productRepository.save(product);
                });
        if (updateProduct.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("failed", "Product not found!", updateProduct)
            );
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "Update successfully!", updateProduct)
            );
        }
    }

    @Override
    public ResponseEntity<ResponseObject> deleteProduct(int id) {
        Optional<Product> deleteProduct = productRepository.findById(id)
                .map(product -> {
                    product.setStatus(false);
                    return productRepository.save(product);
                });
        if (!deleteProduct.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "Delete successfully!", "")
            );
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("failed", "Product not found!", "")
            );
        }
    }
}
