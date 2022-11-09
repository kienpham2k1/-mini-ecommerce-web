package com.springboot.miniecommercewebapp.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.springboot.miniecommercewebapp.models.enums.EProductStatus;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

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
    @NotNull
    private int productId;
    @Basic
    @Column(name = "quantity", nullable = false)
    @Min(1)
    private int quantity;
    @Basic
    @Column(name = "price", nullable = false, precision = 0)
    @Min(0)
    private double price;
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "userID", referencedColumnName = "userID", nullable = false, insertable = false, updatable = false)
    private UsersEntity tblUsersByUserId;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "productID", referencedColumnName = "productID", nullable = false, insertable = false, updatable = false)
    private ProductsEntity tblProductsByProductId;
    @Transient
    private boolean Valid;

    public boolean isValid() {
        if (tblProductsByProductId == null) return false;
        if (tblProductsByProductId.getQuantity() > 0 && tblProductsByProductId.getStatus().equals(EProductStatus.STOCKING))
            return true;
        return false;
    }
}
