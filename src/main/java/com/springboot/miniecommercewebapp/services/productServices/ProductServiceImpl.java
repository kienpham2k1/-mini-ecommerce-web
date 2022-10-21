package com.springboot.miniecommercewebapp.services.productServices;

import com.springboot.miniecommercewebapp.models.Product;
import com.springboot.miniecommercewebapp.exceptions.ResponseObject;
import com.springboot.miniecommercewebapp.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.Date;
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
        Page<Product> pg = productRepository.findAll(pageable);
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
    public ResponseEntity<ResponseObject> addNewProduct(Product newProduct) {
        List<Product> foundProducts = productRepository.findByProductName(newProduct.getProductName().trim());

        if (foundProducts.size() > 0)
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
                    new ResponseObject("failed", "Insert not successfully, product name already taken!", null)
            );
        else
            newProduct.setCreatedDate(Date.valueOf(java.time.LocalDate.now()));
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "Insert successfully!", productRepository.save(newProduct))
        );
    }

    @Override
    public ResponseEntity<ResponseObject> updateProduct(Product updateProduct, int productId, int quantity) {
        Optional<Product> foundProduct = productRepository.findById(productId)
                .map(product -> {
                    product.setProductName(updateProduct.getProductName());
                    product.setImage(updateProduct.getImage());
                    product.setPrice(updateProduct.getPrice());
                    product.setQuantity(updateProduct.getQuantity() - quantity);
                    product.setCatagoryId(updateProduct.getCatagoryId());
//                    if ((updateProduct.getQuantity() - quantity) == 0) {
//                        // Set status out of stock
//                    } else
                    product.setStatus(updateProduct.isStatus());
                    product.setDescription(updateProduct.getDescription());
                    product.setCreatedDate(Date.valueOf(java.time.LocalDate.now()));
                    return productRepository.save(product);
                });
        if (foundProduct.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("failed", "Product not found!", "")
            );
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "Update successfully!", updateProduct)
            );
        }
    }

    @Override
    public ResponseEntity<ResponseObject> updateProduct(int id, int quantity) {
        Optional<Product> cs = productRepository.findById(id);
        Optional<Product> foundProduct = productRepository.findById(id)
                .map(product -> {
                    product.setQuantity(cs.get().getQuantity() - quantity);
//                    if ((updateProduct.getQuantity() - quantity) == 0) {
//                        // Set status out of stock
//                    } else
                    return productRepository.save(product);
                });
        if (foundProduct.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("failed", "Product not found!", "")
            );
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "Update successfully!", cs)
            );
        }
    }

    @Override
    public ResponseEntity<ResponseObject> updateStatusProduct(int id, int newStatus) {
        Optional<Product> updateProduct = productRepository.findById(id)
                .map(product -> {
                    product.setStatus(false);
//                    product.setStatus(newStatus);
                    return productRepository.save(product);
                });
        if (updateProduct.isPresent()) {
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
