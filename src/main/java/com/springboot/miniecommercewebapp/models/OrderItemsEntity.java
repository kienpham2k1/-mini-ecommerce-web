package com.springboot.miniecommercewebapp.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Entity
@Data
@RequiredArgsConstructor
@Table(name = "tblOrderItems", schema = "dbo", catalog = "MiniEcommerce")
public class OrderItemsEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "detailID", nullable = false)
    private int detailId;
    @Basic
    @Column(name = "quantity", nullable = false)
    @Min(value = 1)
    private int quantity;
    @Basic
    @Column(name = "price", nullable = false, precision = 0)
    @DecimalMin(value = "0.1")
    private double price;
    @Basic
    @Column(name = "orderID", nullable = true)
    @NotNull
    private Integer orderId;
    @Basic
    @Column(name = "productID", nullable = false)
    @NotNull
    private int productId;
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "orderID", referencedColumnName = "orderID", insertable = false, updatable = false)
    private OrdersEntity tblOrdersByOrderId;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "productID", referencedColumnName = "productID", nullable = false, insertable = false, updatable = false)
    private ProductsEntity tblProductsByProductId;

    public OrderItemsEntity(int quantity, double price, Integer orderId, int productId) {
        this.quantity = quantity;
        this.price = price;
        this.orderId = orderId;
        this.productId = productId;
    }
}