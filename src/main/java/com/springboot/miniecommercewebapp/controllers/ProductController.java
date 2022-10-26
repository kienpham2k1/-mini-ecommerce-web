package com.springboot.miniecommercewebapp.controllers;

import com.springboot.miniecommercewebapp.models.ProductsEntity;
import com.springboot.miniecommercewebapp.repositories.ProductRepository;
import com.springboot.miniecommercewebapp.services.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "products")
public class ProductController {
    @Autowired
    IProductService iProductService;
    @Autowired
    ProductRepository productRepository;

    // Get all products
//    @GetMapping()
////    @PreAuthorize("hasAuthority('ROLE_USER')")
//    ResponseEntity<?> getAllProducts() {
//        List<ProductsEntity> listProducts = iProductService.getAllProducts();
//        return ResponseEntity.status(HttpStatus.OK).body(new SuccessResponse("200", "Successfully found list of products", listProducts));
//    }

    @GetMapping()
    ResponseEntity<?> getAllProducts(@RequestParam(name = "page", required = false, defaultValue = "0") Integer page
            , @RequestParam(name = "size", required = true, defaultValue = "10") Integer size
            , @RequestParam(name = "sortTable", required = true, defaultValue = "productId") String sortTable
            , @RequestParam(name = "sort", required = true, defaultValue = "ASC") String sort) {
        return new ResponseEntity<>(iProductService.getProductsWithPage(page, size, sortTable, sort).getContent(), HttpStatus.OK);
    }

    // Get products by category id
    @GetMapping("/{categoryId}/category")
    ResponseEntity<?> getProductsByCategoryId(@PathVariable int categoryId) {
        return new ResponseEntity<>(iProductService.getProductsByCategoryId(categoryId), HttpStatus.OK);
    }

    // Get detail product by id
    @GetMapping("/{productId}")
    ResponseEntity<?> getProductById(@PathVariable int productId) {
        return new ResponseEntity<>(iProductService.getProductById(productId), HttpStatus.OK);
    }

    // Add product
    @PostMapping()
    ResponseEntity<?> addNewProduct(@Valid @RequestBody ProductsEntity newProduct) {
        return new ResponseEntity<>(iProductService.addNewProduct(newProduct), HttpStatus.CREATED);
    }

    // Update Product
    @PutMapping("/{productId}")
    ResponseEntity<?> updateProduct(@PathVariable int productId, @RequestBody ProductsEntity newProduct) {
        return new ResponseEntity<>(iProductService.updateProduct(productId, newProduct, 0), HttpStatus.OK);
    }

    // Delete product by id
    @DeleteMapping("/{productId}")
    ResponseEntity<?> deleteProduct(@PathVariable int productId, @RequestParam(name = "status") int newStatus) {
        return new ResponseEntity<>(iProductService.updateStatusProduct(productId, newStatus), HttpStatus.NO_CONTENT);
    }

}
