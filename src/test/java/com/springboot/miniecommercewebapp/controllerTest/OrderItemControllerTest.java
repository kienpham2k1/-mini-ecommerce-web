package com.springboot.miniecommercewebapp.controllerTest;

import com.springboot.miniecommercewebapp.controllers.OrderItemController;
import com.springboot.miniecommercewebapp.models.OrderItemsEntity;
import com.springboot.miniecommercewebapp.services.IOrderItemService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class OrderItemControllerTest {
    @Mock
    IOrderItemService iOrderItemService;
    @InjectMocks
    OrderItemController orderItemController;
    OrderItemsEntity orderItemsEntity1;
    List<OrderItemsEntity> list = new ArrayList<>();

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        orderItemsEntity1 = new OrderItemsEntity();
        orderItemsEntity1.setDetailId(1);
        orderItemsEntity1.setOrderId(1);
        orderItemsEntity1.setProductId(1);
        orderItemsEntity1.setQuantity(1);
        orderItemsEntity1.setPrice(1000);
        for (int i = 0; i <= 20; i++) {
            list.add(orderItemsEntity1);
        }
    }

    @Test
    public void testGetAllOrderItemsByOrderId() {
        Mockito.when(iOrderItemService.getOrderItemsByOrderId(1)).thenReturn(list);
        Assertions.assertEquals(orderItemController.getAllOrderItemsByOrderId(1).getStatusCodeValue(), 200);
    }
}

