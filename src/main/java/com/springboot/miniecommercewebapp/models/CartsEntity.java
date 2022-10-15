package com.springboot.miniecommercewebapp.models;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "tblCarts", schema = "dbo", catalog = "MiniEcommerce")
public class CartsEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "cartID", nullable = false)
    private int cartId;
    @Basic
    @Column(name = "userID", nullable = false)
    private int userId;
    @Basic
    @Column(name = "productID", nullable = false)
    private int productId;
    @Basic
    @Column(name = "quantity", nullable = false)
    private int quantity;
    @ManyToOne
    @JoinColumn(name = "userID", referencedColumnName = "userID", nullable = false,
            insertable = false, updatable=false)
    private UsersEntity tblUsersByUserId;
    @ManyToOne
    @JoinColumn(name = "productID", referencedColumnName = "productID", nullable = false,
            insertable = false, updatable=false)
    private ProductsEntity tblProductsByProductId;

    public int getCartId() {
        return cartId;
    }

    public void setCartId(int cartId) {
        this.cartId = cartId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CartsEntity that = (CartsEntity) o;
        return cartId == that.cartId && userId == that.userId && productId == that.productId && quantity == that.quantity;
    }

    @Override
    public int hashCode() {
        return Objects.hash(cartId, userId, productId, quantity);
    }

    public UsersEntity getTblUsersByUserId() {
        return tblUsersByUserId;
    }

    public void setTblUsersByUserId(UsersEntity tblUsersByUserId) {
        this.tblUsersByUserId = tblUsersByUserId;
    }

    public ProductsEntity getTblProductsByProductId() {
        return tblProductsByProductId;
    }

    public void setTblProductsByProductId(ProductsEntity tblProductsByProductId) {
        this.tblProductsByProductId = tblProductsByProductId;
    }
}
