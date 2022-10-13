package com.springboot.miniecommercewebapp.models;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "tblcarts", schema = "miniecommerce", catalog = "")
public class TblcartsEntity {
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
    @JoinColumn(name = "userID", referencedColumnName = "userID", nullable = false)
    private TblusersEntity tblusersByUserId;
    @ManyToOne
    @JoinColumn(name = "productID", referencedColumnName = "productID", nullable = false)
    private TblproductsEntity tblproductsByProductId;

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
        TblcartsEntity that = (TblcartsEntity) o;
        return cartId == that.cartId && userId == that.userId && productId == that.productId && quantity == that.quantity;
    }

    @Override
    public int hashCode() {
        return Objects.hash(cartId, userId, productId, quantity);
    }

    public TblusersEntity getTblusersByUserId() {
        return tblusersByUserId;
    }

    public void setTblusersByUserId(TblusersEntity tblusersByUserId) {
        this.tblusersByUserId = tblusersByUserId;
    }

    public TblproductsEntity getTblproductsByProductId() {
        return tblproductsByProductId;
    }

    public void setTblproductsByProductId(TblproductsEntity tblproductsByProductId) {
        this.tblproductsByProductId = tblproductsByProductId;
    }
}
