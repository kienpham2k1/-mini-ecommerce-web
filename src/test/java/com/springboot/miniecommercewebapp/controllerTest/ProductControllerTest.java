package com.springboot.miniecommercewebapp.controllerTest;

import com.springboot.miniecommercewebapp.controllers.ProductController;
import com.springboot.miniecommercewebapp.dto.response.SuccessResponse;
import com.springboot.miniecommercewebapp.exceptions.NotFoundException;
import com.springboot.miniecommercewebapp.models.ProductsEntity;
import com.springboot.miniecommercewebapp.models.enums.EProductStatus;
import com.springboot.miniecommercewebapp.repositories.ProductRepository;
import com.springboot.miniecommercewebapp.services.IProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;


//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
public class ProductControllerTest {
    @Mock
    IProductService iProductService;
    @InjectMocks
    ProductController productController;
    @Mock
    ProductRepository productRepository;
    ProductsEntity products;
    ProductsEntity products2;
    List<ProductsEntity> listProd = new ArrayList<>();

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        products = new ProductsEntity();
        products.setProductId(1);
        products.setProductName("Product 1");
        products.setQuantity(1);
        products.setStatus(EProductStatus.STOCKING);
        products2 = new ProductsEntity();
        products2.setProductId(2);
        products2.setProductName("Product 2");
        products2.setQuantity(0);
        products2.setStatus(EProductStatus.OUT_OF_STOCK);
        for (int i = 0; i <= 20; i++) {
            products.setProductId(i);
            listProd.add(products);
        }
    }

    @Test
    public void testGetAllProducts() {
        int page = 0;
        int size = 5;
        String sortable = "productId";
        String sort = "ASC";
        String nameProduct = null;
        Integer categoryId = null;
        ;
        Pageable pageProd = null;
        Page<ProductsEntity> procductPage = null;
        pageProd = PageRequest.of(page, size, Sort.by(sortable.trim()).ascending());
        procductPage = new PageImpl<>(listProd, pageProd, 20);
        when(iProductService.getProductsWithPage(page, size, sortable, sort, nameProduct, categoryId))
                .thenReturn(procductPage);
        assertEquals(productController.getAllProducts(page, size, sortable, sort, nameProduct, categoryId).getStatusCode()
                , HttpStatus.OK);

    }

    @Test
    public void testGetProductById_success() throws Exception {
        when(iProductService.getProductById(1)).thenReturn(Optional.ofNullable(products));
        assertEquals(productController.getProductById(1).getStatusCode(), HttpStatus.OK);
        SuccessResponse successResponse = (SuccessResponse) productController.getProductById(1).getBody();
        assertEquals(Optional.of(products), successResponse.getData());
    }

    @Test
    public void testAddNewProduct() {
        when(iProductService.addNewProduct(products)).thenReturn(products);
        assertEquals(productController.addNewProduct(products).getStatusCode(), HttpStatus.CREATED);
        SuccessResponse successResponse = (SuccessResponse) productController.addNewProduct(products).getBody();
        assertEquals(products, successResponse.getData());
    }

    @Test
    public void testUpdateProduct() {
        when(iProductService.updateProduct(1, products)).thenReturn(products2);
        assertEquals(productController.updateProduct(1, products).getStatusCode(), HttpStatus.OK);
        SuccessResponse successResponse = (SuccessResponse) productController.updateProduct(1, products).getBody();
        assertEquals(products2, successResponse.getData());
    }

    @Test
    public void testUpdateStatus_success() {
        when(iProductService.updateStatusProduct(1, "OUT_OF_STOCK")).thenReturn(true);
        assertEquals(productController.updateStatus(1, "OUT_OF_STOCK").getStatusCode(), HttpStatus.OK);
    }

    @Test
    public void testUpdateStatus_failed() {
//        when(productRepository.findById(1)).thenReturn(Optional.ofNullable(null));
        when(iProductService.updateStatusProduct(1, "OUT_OF_STOCK")).thenThrow(NotFoundException.class);
        assertThrows(NotFoundException.class, () -> productController.updateStatus(1, "OUT_OF_STOCK"));
    }

}
