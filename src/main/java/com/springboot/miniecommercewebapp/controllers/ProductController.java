package com.springboot.miniecommercewebapp.controllers;

import com.springboot.miniecommercewebapp.models.Product;
import com.springboot.miniecommercewebapp.models.ResponseObject;
import com.springboot.miniecommercewebapp.services.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/products")
public class ProductController {
    @Autowired
    IProductService iProductService;

    // Get all products
    @GetMapping("")
    ResponseEntity<ResponseObject> getAllProducts() {
        return iProductService.getAllProducts();
    }

    @GetMapping("/pagination")
    ResponseEntity<ResponseObject> getAllProducts(@RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
                                                  @RequestParam(name = "size", required = false, defaultValue = "2") Integer size,
                                                  @RequestParam(name = "sortTable", required = false, defaultValue = "productID") String sortTable,
                                                  @RequestParam(name = "sort", required = false, defaultValue = "ASC") String sort) {
        return iProductService.getProductsWithPage(page, size, sortTable, sort);
    }

    // Get products by category id
    @GetMapping("categoryId/{categoryId}")
    ResponseEntity<ResponseObject> getProductsByCategoryId(@PathVariable int categoryId) {
        return iProductService.getProductsByCategoryId(categoryId);
    }

    //
//    // Get detail product by id
    @GetMapping("/ProductId/{productId}")
    ResponseEntity<ResponseObject> getProductById(@PathVariable int productId) {
        return iProductService.getProductDetailById(productId);
    }

    // Add product
    @PostMapping("")
    ResponseEntity<ResponseObject> addNewProduct(@RequestBody Product newProduct) {
        return iProductService.addNewProduct(newProduct);
    }

    // Update Product
    @PutMapping("/{productId}")
    ResponseEntity<ResponseObject> updateProduct(@RequestBody Product newProduct, @PathVariable int productId) {
        return iProductService.updateProduct(newProduct, productId, 0);
    }

    // Delete product by id
    @DeleteMapping("/{productId}")
    ResponseEntity<ResponseObject> deleteProduct(@PathVariable int productId) {
        return iProductService.deleteProduct(productId);
    }

}
