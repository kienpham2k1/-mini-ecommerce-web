package com.springboot.miniecommercewebapp.models;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "tblProducts", schema = "dbo", catalog = "MiniEcommerce")
public class ProductsEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "productID", nullable = false)
    private int productId;
    @Basic
    @Column(name = "productName", nullable = false, length = 100)
    private String productName;
    @Basic
    @Column(name = "image", nullable = false, length = -1)
    private String image;
    @Basic
    @Column(name = "price", nullable = false, precision = 0)
    private double price;
    @Basic
    @Column(name = "quantity", nullable = false)
    private int quantity;
    @Basic
    @Column(name = "catagoryID", nullable = false)
    private int catagoryId;
    @Basic
    @Column(name = "status", nullable = false)
    private boolean status;
    @OneToMany(mappedBy = "tblProductsByProductId")
    private Collection<CartsEntity> tblCartsByProductId;
    @OneToMany(mappedBy = "tblProductsByProductId")
    private Collection<OrderDetailsEntity> tblOrderDetailsByProductId;
    @ManyToOne
    @JoinColumn(name = "catagoryID", referencedColumnName = "catagoryID", nullable = false,
            insertable = false, updatable=false)
    private CategoriesEntity tblCategoriesByCatagoryId;
    @OneToMany(mappedBy = "tblProductsByProductId")
    private Collection<RatingsEntity> tblRatingsByProductId;

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getCatagoryId() {
        return catagoryId;
    }

    public void setCatagoryId(int catagoryId) {
        this.catagoryId = catagoryId;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductsEntity that = (ProductsEntity) o;
        return productId == that.productId && Double.compare(that.price, price) == 0 && quantity == that.quantity && catagoryId == that.catagoryId && status == that.status && Objects.equals(productName, that.productName) && Objects.equals(image, that.image);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId, productName, image, price, quantity, catagoryId, status);
    }

    public Collection<CartsEntity> getTblCartsByProductId() {
        return tblCartsByProductId;
    }

    public void setTblCartsByProductId(Collection<CartsEntity> tblCartsByProductId) {
        this.tblCartsByProductId = tblCartsByProductId;
    }

    public Collection<OrderDetailsEntity> getTblOrderDetailsByProductId() {
        return tblOrderDetailsByProductId;
    }

    public void setTblOrderDetailsByProductId(Collection<OrderDetailsEntity> tblOrderDetailsByProductId) {
        this.tblOrderDetailsByProductId = tblOrderDetailsByProductId;
    }

    public CategoriesEntity getTblCategoriesByCatagoryId() {
        return tblCategoriesByCatagoryId;
    }

    public void setTblCategoriesByCatagoryId(CategoriesEntity tblCategoriesByCatagoryId) {
        this.tblCategoriesByCatagoryId = tblCategoriesByCatagoryId;
    }

    public Collection<RatingsEntity> getTblRatingsByProductId() {
        return tblRatingsByProductId;
    }

    public void setTblRatingsByProductId(Collection<RatingsEntity> tblRatingsByProductId) {
        this.tblRatingsByProductId = tblRatingsByProductId;
    }
}
