package com.springboot.miniecommercewebapp.serviceTest;

import com.springboot.miniecommercewebapp.exceptions.NotFoundException;
import com.springboot.miniecommercewebapp.models.CartsEntity;
import com.springboot.miniecommercewebapp.models.OrderItemsEntity;
import com.springboot.miniecommercewebapp.models.OrdersEntity;
import com.springboot.miniecommercewebapp.models.ProductsEntity;
import com.springboot.miniecommercewebapp.repositories.CartRepository;
import com.springboot.miniecommercewebapp.repositories.OrderItemRepository;
import com.springboot.miniecommercewebapp.repositories.OrderRepository;
import com.springboot.miniecommercewebapp.repositories.ProductRepository;
import com.springboot.miniecommercewebapp.services.ICartService;
import com.springboot.miniecommercewebapp.services.IOrderItemService;
import com.springboot.miniecommercewebapp.services.IProductService;
import com.springboot.miniecommercewebapp.services.Impl.OrderItemServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
public class OrderItemServiceTest {
    @Mock
    OrderItemRepository orderItemRepository;
    @Mock
    OrderRepository orderRepository;
    @Mock
    ProductRepository productRepository;
    @Mock
    CartRepository cartRepository;
    @Mock
    ICartService iCartService;
    @InjectMocks
    IOrderItemService iOrderItemService = new OrderItemServiceImpl();
    @Mock
    IProductService iProductService;
    OrderItemsEntity orderItemsEntity1;
    OrderItemsEntity orderItemsEntity2;
    ProductsEntity productsEntity;
    OrdersEntity ordersEntity;
    CartsEntity cartsEntity;
    List<OrderItemsEntity> list = new ArrayList<>();

    @BeforeEach
    public void setup() {
        orderItemsEntity1 = new OrderItemsEntity();
        orderItemsEntity1.setOrderId(1);
        orderItemsEntity1.setDetailId(1);
        orderItemsEntity1.setProductId(1);
        orderItemsEntity1.setQuantity(10);
        orderItemsEntity1.setPrice(500);
        for (int i = 0; i <= 10; i++) {
            list.add(orderItemsEntity1);
        }
        productsEntity = new ProductsEntity();
        productsEntity.setProductId(1);
        productsEntity.setQuantity(1);
        ordersEntity = new OrdersEntity();
        ordersEntity.setOrderId(1);
        ordersEntity.setUserId("user1");
        cartsEntity = new CartsEntity();
        cartsEntity.setCartId(1);
        cartsEntity.setProductId(1);
        cartsEntity.setPrice(1);
        cartsEntity.setUserId("user1");
        cartsEntity.setQuantity(1);
    }

    @Test
    @WithMockUser(username = "user1", roles = {"USER"})
    void testGetOrderItemsByOrderId_success1() {
//        when(authentication.getName()).thenReturn("user1");
        when(orderRepository.findById(1)).thenReturn(Optional.of(ordersEntity));
        when(orderItemRepository.findByOrderId(1)).thenReturn(list);
        assertEquals(list, iOrderItemService.getOrderItemsByOrderId(1));
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    void testGetOrderItemsByOrderId_failed_user() {
//        when(authentication.getName()).thenReturn("user1");
        when(orderRepository.findById(1)).thenReturn(Optional.of(ordersEntity));
        when(orderItemRepository.findByOrderId(1)).thenReturn(list);
        assertThatThrownBy(() -> iOrderItemService.getOrderItemsByOrderId(1)).isInstanceOf(AccessDeniedException.class);
    }

    @Test
    void testGetOrderItemsByOrderId_failed() {
        //when(orderRepository.findById(1)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> iOrderItemService.getOrderItemsByOrderId(1)).isInstanceOf(NotFoundException.class);
    }

    @Test
    void testCancelOrderDetail_success() {
        when(orderItemRepository.findById(1)).thenReturn(Optional.ofNullable(orderItemsEntity1));
        when(productRepository.findById(1)).thenReturn(Optional.ofNullable(productsEntity));
        when(iProductService.updateProduct(1, 1)).thenReturn(productsEntity);
        when(productRepository.findById(1)).thenReturn(Optional.ofNullable(productsEntity));
        when(productRepository.save(productsEntity)).thenReturn(productsEntity);
        assertEquals(true, iOrderItemService.cancelOrderDetail(1));
    }

    @Test
    void testCancelOrderDetail_failed1() {
        when(orderItemRepository.findById(1)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> iOrderItemService.cancelOrderDetail(1)).isInstanceOf(NotFoundException.class);
    }

    @Test
    void testCancelOrderDetail_failed() {
        when(orderItemRepository.findById(1)).thenReturn(Optional.ofNullable(orderItemsEntity1));
        when(productRepository.findById(1)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> iOrderItemService.cancelOrderDetail(1)).isInstanceOf(NotFoundException.class);
    }

    @Test
    void testAddOrderItem_failed1() {
        when(productRepository.findByProductIdAndQuantityGreaterThanEqualAndStatusEqualsIgnoreCase(1, 1)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> iOrderItemService.addOrderItem(1, cartsEntity)).isInstanceOf(NotFoundException.class);
    }

    @Test
    void testAddOrderItem_failed2() {
        when(productRepository.findByProductIdAndQuantityGreaterThanEqualAndStatusEqualsIgnoreCase(1, 1)).thenReturn(Optional.ofNullable(productsEntity));
        when(orderRepository.findById(1)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> iOrderItemService.addOrderItem(1, cartsEntity)).isInstanceOf(NotFoundException.class);
    }

    @Test
    void testAddOrderItem_failed3() {
        when(productRepository.findByProductIdAndQuantityGreaterThanEqualAndStatusEqualsIgnoreCase(1, 1)).thenReturn(Optional.ofNullable(productsEntity));
        when(orderRepository.findById(1)).thenReturn(Optional.ofNullable(ordersEntity));
        when(cartRepository.findById(1)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> iOrderItemService.addOrderItem(1, cartsEntity)).isInstanceOf(NotFoundException.class);
    }

    @Test
    void testAddOrderItem_success() {
        when(productRepository.findByProductIdAndQuantityGreaterThanEqualAndStatusEqualsIgnoreCase(1, 1)).thenReturn(Optional.of(productsEntity));
        when(orderRepository.findById(1)).thenReturn(Optional.ofNullable(ordersEntity));
        when(cartRepository.findById(1)).thenReturn(Optional.ofNullable(cartsEntity));

    orderItemsEntity2 = new OrderItemsEntity();
        orderItemsEntity2.setOrderId(1);
        orderItemsEntity2.setQuantity(1);
        orderItemsEntity2.setPrice(1);
        orderItemsEntity2.setProductId(1);
        when(orderItemRepository.save(orderItemsEntity2)).thenReturn(orderItemsEntity2);
        when(iCartService.deleteCartItem(1)).thenReturn(true);
        when(iProductService.updateProduct(1, 1)).thenReturn(productsEntity);
        assertEquals(orderItemsEntity2, iOrderItemService.addOrderItem(1, cartsEntity));
    }


}
