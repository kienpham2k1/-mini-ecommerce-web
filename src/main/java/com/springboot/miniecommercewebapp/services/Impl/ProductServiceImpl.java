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
    public Page<ProductsEntity> getProductsWithPage(int page, int size, String sortable, String sort, String nameProduct,
                                                  Integer categoryId) {
        Page<ProductsEntity> procductPage = null;
        Pageable pageProd = null;
        if (sort.equals("ASC")) {
            pageProd = PageRequest.of(page, size, Sort.by(sortable.trim()).ascending());
        } else if (sort.equals("DESC")) {
            pageProd = PageRequest.of(page, size, Sort.by(sortable.trim()).descending());
        }
            procductPage = productRepository.findAll(pageProd);
        if(categoryId != null && nameProduct != null){
            procductPage = productRepository.findAllByProductNameContainingIgnoreCaseAndCatagoryId(nameProduct.trim(), categoryId,
                    pageProd);
        }else if(nameProduct != null){
            procductPage =  productRepository.findAllByProductNameContainingIgnoreCase(nameProduct.trim(), pageProd);
        }else if(categoryId != null){
            procductPage = productRepository.findByCatagoryId(categoryId, pageProd);
        }
        if (procductPage.getContent().size() > 0)
            return procductPage;
        throw new NotFoundException("Failed to found product");
    }

    @Override
    public List<ProductsEntity> getProductsByCategoryId(int categoryId) {
//        List<ProductsEntity> listProducts = productRepository.findByCatagoryId(categoryId);
//        if (listProducts.size() > 0)
//            return listProducts;
//        throw new NotFoundException("Not found any product");
        return  null;
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
    public ProductsEntity updateProduct(int productId, ProductsEntity updateProduct, int quantity) {
        Optional<ProductsEntity> foundProduct = productRepository.findById(productId);
        if (foundProduct.isPresent()) {
            Optional<ProductsEntity> foundName = productRepository.findByProductName(updateProduct.getProductName());
//        Check name
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
    public Optional<ProductsEntity> updateProduct(int id, int quantity) {
        Optional<ProductsEntity> availableProduct = productRepository.findById(id);
        if (availableProduct.isPresent()) {
            boolean newStatus = (availableProduct.get().getQuantity() - quantity) != 0;
            availableProduct.map(product -> {
                product.setQuantity(availableProduct.get().getQuantity() - quantity);
                //product.setStatus(1);
                return productRepository.save(product);
            });
        }
        return availableProduct;
    }

    @Override
    public boolean updateStatusProduct(int id, String newStatus) {
        Optional<ProductsEntity> updateProduct = productRepository.findById(id)
                .map(product -> {
                    product.setStatus(EProductStatus.valueOf(newStatus));
                    return productRepository.save(product);
                });
        if (updateProduct.isPresent()) {
            return true;
        }
        throw new NotFoundException("Not found product.");
    }
}