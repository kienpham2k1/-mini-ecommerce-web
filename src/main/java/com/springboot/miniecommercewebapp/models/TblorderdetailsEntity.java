package com.springboot.miniecommercewebapp.models;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "tblorderdetails", schema = "miniecommerce", catalog = "")
public class TblorderdetailsEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "detailID", nullable = false)
    private int detailId;
    @Basic
    @Column(name = "quantity", nullable = false)
    private int quantity;
    @Basic
    @Column(name = "price", nullable = false, precision = 0)
    private double price;
    @Basic
    @Column(name = "orderID", nullable = true)
    private Integer orderId;
    @Basic
    @Column(name = "productID", nullable = false)
    private int productId;
    @ManyToOne
    @JoinColumn(name = "orderID", referencedColumnName = "orderID")
    private TblordersEntity tblordersByOrderId;

    public int getDetailId() {
        return detailId;
    }

    public void setDetailId(int detailId) {
        this.detailId = detailId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TblorderdetailsEntity that = (TblorderdetailsEntity) o;
        return detailId == that.detailId && quantity == that.quantity && Double.compare(that.price, price) == 0 && productId == that.productId && Objects.equals(orderId, that.orderId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(detailId, quantity, price, orderId, productId);
    }

    public TblordersEntity getTblordersByOrderId() {
        return tblordersByOrderId;
    }

    public void setTblordersByOrderId(TblordersEntity tblordersByOrderId) {
        this.tblordersByOrderId = tblordersByOrderId;
    }
}
