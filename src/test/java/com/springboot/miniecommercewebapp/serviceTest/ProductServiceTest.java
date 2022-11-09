package com.springboot.miniecommercewebapp.serviceTest;

import com.springboot.miniecommercewebapp.exceptions.ItemExistException;
import com.springboot.miniecommercewebapp.exceptions.NotFoundException;
import com.springboot.miniecommercewebapp.models.ProductsEntity;
import com.springboot.miniecommercewebapp.models.enums.EProductStatus;
import com.springboot.miniecommercewebapp.repositories.ProductRepository;
import com.springboot.miniecommercewebapp.services.IProductService;
import com.springboot.miniecommercewebapp.services.Impl.ProductServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.*;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;
    @InjectMocks
    private IProductService iProductService = new ProductServiceImpl();
    private static List<ProductsEntity> list = new ArrayList<>();

    @BeforeAll
    static void beforeAll() {
        String date = "2015-03-31";
        ProductsEntity product = new ProductsEntity();
        product.setProductId(1);
        product.setProductName("Product");
        for (int i = 0; i < 5; i++) {
            //            product.setProductId(i);
            list.add(product);
        }
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void testGetProductsWithPage() {
        int page = 0;
        int size = 5;
        String sortable = "productId";
        String sort = "ASC";
        String nameProduct = null;
        Integer categoryId = null;
        Pageable pageProd = null;
        Page<ProductsEntity> procductPage = null;
        pageProd = PageRequest.of(page, size, Sort.by(sortable.trim()).ascending());
        procductPage = new PageImpl<>(list, pageProd, 12);
        when(productRepository.findAll(pageProd)).thenReturn(procductPage);
        assertEquals(5,
                iProductService.getProductsWithPage(page, size, sortable, sort, nameProduct, categoryId).getSize());
        categoryId = 1;
        when(productRepository.findByCatagoryId(1, pageProd)).thenReturn(procductPage);
        assertEquals(5,
                iProductService.getProductsWithPage(page, size, sortable, sort, nameProduct, categoryId).getSize());
        categoryId = null;
        nameProduct = "name";
        when(productRepository.findAllByProductNameContainingIgnoreCase("name", pageProd)).thenReturn(procductPage);
        assertEquals(5,
                iProductService.getProductsWithPage(page, size, sortable, sort, nameProduct, categoryId).getSize());
    }

    @Test
    void testGetProductByIdIsFound() {

//        System.out.println(list);
        int id = 1;
        ProductsEntity product = new ProductsEntity();
        product.setProductId(1);
        when(productRepository.findById(any(Integer.class))).thenReturn(Optional.of(product));
        assertEquals(id, iProductService.getProductById(id).get().getProductId());
        assertEquals(product, iProductService.getProductById(id).get());
    }

    @Test
    void testGetProductByIdIsNotFound() {
        int invalidProductId = 1;
        when(productRepository.findById(any(Integer.class))).thenReturn(Optional.ofNullable(null));
        assertThatThrownBy(() -> iProductService.getProductById(invalidProductId)).isInstanceOf(NotFoundException.class);
        verify(productRepository).findById(any(Integer.class));
    }

    @Test
    void testAddNewProductIsValid() {
        String validName = "Valid name product";
        ProductsEntity product = new ProductsEntity();
        product.setProductId(1);
        product.setProductName(validName);
        when(productRepository.findByProductName(any(String.class))).thenReturn(Optional.ofNullable(null));
        when(productRepository.save(product)).thenReturn(product);
        assertEquals(product.getProductId(), iProductService.addNewProduct(product).getProductId());
        assertEquals(product, iProductService.addNewProduct(product));
    }

    @Test
    void testAddNewProductIsInvalid() {
        String InvalidName = "Invalid name product";
        ProductsEntity productsFound = new ProductsEntity();
        productsFound.setProductId(1);
        productsFound.setProductName("Product name");
//        String date = "2015-03-31";
//        ProductsEntity product = new ProductsEntity(1, "Product 1", "This is description of product 1"
//                , Date.valueOf(date), 100000, 20, 1, EProductStatus.STOCKING, "/image1.jpg"
//                , null, null, null, null, null
//                , 5);
        when(productRepository.findByProductName(any(String.class))).thenReturn(Optional.of(productsFound));
        assertThatThrownBy(() -> iProductService.addNewProduct(productsFound)).isInstanceOf(ItemExistException.class);
        verify(productRepository).findByProductName(any(String.class));
    }


    @Test
    void testUpdateProduct() {
        int id = 1;
        String newProductName = "12345----Product";
        ProductsEntity product1 = new ProductsEntity();
        product1.setProductId(1);
        product1.setProductName("Product 12345");
        product1.setStatus(EProductStatus.STOCKING);
        ProductsEntity product2 = new ProductsEntity();
        product2.setProductName(newProductName);
        when(productRepository.findById(any(Integer.class))).thenReturn(Optional.of(product1));
        when(productRepository.findByProductName(any(String.class))).thenReturn(Optional.ofNullable(null));
        when(productRepository.save(any(ProductsEntity.class))).thenReturn(product2);
        assertEquals(product2, iProductService.updateProduct(id, product2));
        assertEquals(newProductName, iProductService.updateProduct(id, product2).getProductName());
        assertEquals(EProductStatus.OUT_OF_STOCK, iProductService.updateProduct(id, product2).getStatus());
    }

    @Test
    void testUpdateProductIsInValidId() {
        ProductsEntity product = new ProductsEntity();
        product.setProductId(1);
        product.setProductName("Product 1");
        when(productRepository.findById(any(Integer.class))).thenReturn(Optional.ofNullable(null));
        assertThatThrownBy(() -> iProductService.updateProduct(any(Integer.class), product)).isInstanceOf(NotFoundException.class);
//        verify(productRepository).findById(any(Integer.class));
        when(productRepository.findById(any(Integer.class))).thenReturn(Optional.ofNullable(product));
        when(productRepository.findByProductName(any(String.class))).thenReturn(Optional.ofNullable(product));
        assertThatThrownBy(() -> iProductService.updateProduct(any(Integer.class), product)).isInstanceOf(ItemExistException.class);
    }

    @Test
    void testUpdateQuantityProduct() {
        int previousQuantity = 20;
        int quanntityPar = 5;
        int newQuantity = previousQuantity - quanntityPar;
        ProductsEntity product = new ProductsEntity();
        product.setProductId(1);
        product.setProductName("Produt Name");
        product.setStatus(EProductStatus.STOCKING);
        product.setQuantity(previousQuantity);


        ProductsEntity product2 = new ProductsEntity();
        product2.setProductId(1);
        product2.setProductName("Produt Name");
        product2.setStatus(EProductStatus.STOCKING);
        product2.setQuantity(newQuantity);
        when(productRepository.findById(any(Integer.class))).thenReturn(Optional.of(product));
        when(productRepository.save(any(ProductsEntity.class))).thenReturn(product2);
        assertEquals(product2, iProductService.updateProduct(any(Integer.class), quanntityPar));
        verify(productRepository).findById(any(Integer.class));
    }

    @Test
    void testUpdateQuantityProductIsInvalid() {
        int quanntityPar = 5;
        when(productRepository.findById(any(Integer.class))).thenReturn(Optional.ofNullable(null));
        assertThatThrownBy(() -> iProductService.updateProduct(any(Integer.class), quanntityPar)).isInstanceOf(NotFoundException.class);
    }

    @Test
    void testUpdateStatusProduct() {
        int id = 1;
        String newStatus = "STOCKING";
        String date = "2015-03-31";
        ProductsEntity product = new ProductsEntity();
        product.setQuantity(1);
        product.setStatus(EProductStatus.OUT_OF_STOCK);
        when(productRepository.findById(id)).thenReturn(Optional.of(product));
        when(productRepository.save(product)).thenReturn(product);
        assertEquals(true, iProductService.updateStatusProduct(id, newStatus));
    }

    @Test
    void testUpdateStatusProductIsInvalid() {
        String newStatus = "STOCKING";
        when(productRepository.findById(any(Integer.class))).thenReturn(Optional.ofNullable(null));
        assertThatThrownBy(() -> iProductService.updateStatusProduct(any(Integer.class), newStatus)).isInstanceOf(NotFoundException.class);
    }
}
