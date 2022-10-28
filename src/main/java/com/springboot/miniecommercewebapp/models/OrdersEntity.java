package com.springboot.miniecommercewebapp.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.springboot.miniecommercewebapp.models.enums.EOrderStatus;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.sql.Date;
import java.util.Collection;

@Entity
@Data
@Table(name = "tblOrders", schema = "dbo", catalog = "MiniEcommerce")
public class OrdersEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "orderID", nullable = false)
    private int orderId;
    @Basic
    @Column(name = "orderDate", nullable = false)
    private Date orderDate;
    @Basic
    @NotNull
    @Min(value = 0)
    @Column(name = "total", nullable = false, precision = 0)
    private double total;
    @Basic
    @Column(name = "userID", nullable = false, length = 20)
    private String userId;
    @Basic
    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private EOrderStatus status;
    @OneToMany(mappedBy = "tblOrdersByOrderId")
    @JsonIgnore
    private Collection<OrderItemsEntity> tblOrderItemsByOrderId;
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "userID", referencedColumnName = "userID", nullable = false, insertable = false, updatable = false)
    private UsersEntity tblUsersByUserId;
}
