package com.springboot.miniecommercewebapp.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "tblCarts", schema = "dbo", catalog = "MiniEcommerce")
public class CartsEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "cartID", nullable = false)
    private int cartId;
    @Basic
    @Column(name = "userID", nullable = false, length = 20)
    private String userId;
    @Basic
    @Column(name = "productID", nullable = false)
    private int productId;
    @Basic
    @Column(name = "quantity", nullable = false)
    private int quantity;
    @Basic
    @Column(name = "price", nullable = false, precision = 0)
    private double price;
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "userID", referencedColumnName = "userID", nullable = false, insertable = false, updatable = false)
    private UsersEntity tblUsersByUserId;
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "productID", referencedColumnName = "productID", nullable = false, insertable = false, updatable = false)
    private ProductsEntity tblProductsByProductId;
}
