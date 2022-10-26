package com.springboot.miniecommercewebapp.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
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
    @Column(name = "total", nullable = false, precision = 0)
    private double total;
    @Basic
    @Column(name = "userID", nullable = false, length = 20)
    private String userId;
    @Basic
    @Column(name = "status", nullable = false)
    private int status;
    @OneToMany(mappedBy = "tblOrdersByOrderId")
    @JsonIgnore
    private Collection<OrderItemsEntity> tblOrderItemsByOrderId;
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "userID", referencedColumnName = "userID", nullable = false, insertable = false, updatable = false)
    private UsersEntity tblUsersByUserId;
}
