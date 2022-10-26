package com.springboot.miniecommercewebapp.services.Impl;

import com.springboot.miniecommercewebapp.exceptions.ItemExistException;
import com.springboot.miniecommercewebapp.exceptions.NotFoundException;
import com.springboot.miniecommercewebapp.models.ProductsEntity;
import com.springboot.miniecommercewebapp.repositories.ProductRepository;
import com.springboot.miniecommercewebapp.services.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    public List<ProductsEntity> getAllProducts() {
        List<ProductsEntity> listProducts = productRepository.findAll();
        if (listProducts.size() > 0)
            return listProducts;
        throw new NotFoundException("Not found any product");
    }

    @Override
    public Page<ProductsEntity> getProductsWithPage(int page, int size, String sortable, String sort) {
        Page<ProductsEntity> procductPage;// = null;
        Pageable pageProd = null;
        if (sort.equals("ASC")) {
            pageProd = PageRequest.of(page, size, Sort.by(sortable.trim()).ascending());
        } else if (sort.equals("DESC")) {
            pageProd = PageRequest.of(page, size, Sort.by(sortable.trim()).ascending());
        }
        procductPage = productRepository.findAll(pageProd);
        if (procductPage.getContent().size() > 0)
            return productRepository.findAll(pageProd);
        throw new NotFoundException("Failed to found product");
    }

    @Override
    public List<ProductsEntity> getProductsByCategoryId(int categoryId) {
        List<ProductsEntity> listProducts = productRepository.findByCatagoryId(categoryId);
        if (listProducts.size() > 0)
            return listProducts;
        throw new NotFoundException("Not found any product");
    }

    @Override
    public Optional<ProductsEntity> getProductById(int productId) {
        Optional<ProductsEntity> foundProductsEntity = productRepository.findById(productId);
        if (foundProductsEntity.isPresent())
            return foundProductsEntity;
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
        if (foundProducts.isEmpty()) return productRepository.save(newProduct);
        throw new ItemExistException("Product's name has already taken");
    }

    @Override
    public Optional<ProductsEntity> updateProduct(int productId, ProductsEntity updateProduct, int quantity) {
        boolean status = (updateProduct.getQuantity() != 0);
        Optional<ProductsEntity> foundName = productRepository.findByProductName(updateProduct.getProductName());
        if (foundName.isPresent() && foundName.get().getProductId() != productId)
            throw new ItemExistException("This name has already taken");
        Optional<ProductsEntity> foundProduct = productRepository.findById(productId).map(product -> {
            product.setProductName(updateProduct.getProductName());
            product.setPrice(updateProduct.getPrice());
            product.setQuantity(updateProduct.getQuantity() - quantity);
            product.setCatagoryId(updateProduct.getCatagoryId());
            product.setStatus(1);
            product.setDescription(updateProduct.getDescription());
            product.setCreateDate(updateProduct.getCreateDate());
            return productRepository.save(product);
        });
        if (foundProduct.isPresent())
            return foundProduct;
        throw new NotFoundException("Failed! Product not exist to update");
    }

    @Override
    public Optional<ProductsEntity> updateProduct(int id, int quantity) {
        Optional<ProductsEntity> availableProduct = productRepository.findById(id);
        if (availableProduct.isPresent()) {
            boolean newStatus = (availableProduct.get().getQuantity() - quantity) != 0;
            availableProduct.map(product -> {
                product.setQuantity(availableProduct.get().getQuantity() - quantity);
                product.setStatus(1);
                return productRepository.save(product);
            });
        }
        return availableProduct;
    }

    @Override
    public boolean updateStatusProduct(int id, int newStatus) {
        Optional<ProductsEntity> updateProduct = productRepository.findById(id)
                .map(product -> {
                    product.setStatus(1);
//                    product.setStatus(newStatus);
                    return productRepository.save(product);
                });
        if (updateProduct.isPresent())
            return true;
        throw new NotFoundException("Not found product.");
    }
}