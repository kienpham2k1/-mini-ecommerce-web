package com.springboot.miniecommercewebapp.models;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "tblRatings", schema = "dbo", catalog = "MiniEcommerce")
public class RatingsEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "ratingID", nullable = false)
    private int ratingId;
    @Basic
    @Column(name = "productID", nullable = false)
    private int productId;
    @Basic
    @Column(name = "userID", nullable = false)
    private int userId;
    @Basic
    @Column(name = "score", nullable = false)
    private int score;
    @ManyToOne
    @JoinColumn(name = "productID", referencedColumnName = "productID", nullable = false,
            insertable = false, updatable=false)
    private ProductsEntity tblProductsByProductId;
    @ManyToOne
    @JoinColumn(name = "userID", referencedColumnName = "userID", nullable = false,
            insertable = false, updatable=false)
    private UsersEntity tblUsersByUserId;

    public int getRatingId() {
        return ratingId;
    }

    public void setRatingId(int ratingId) {
        this.ratingId = ratingId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RatingsEntity that = (RatingsEntity) o;
        return ratingId == that.ratingId && productId == that.productId && userId == that.userId && score == that.score;
    }

    @Override
    public int hashCode() {
        return Objects.hash(ratingId, productId, userId, score);
    }

    public ProductsEntity getTblProductsByProductId() {
        return tblProductsByProductId;
    }

    public void setTblProductsByProductId(ProductsEntity tblProductsByProductId) {
        this.tblProductsByProductId = tblProductsByProductId;
    }

    public UsersEntity getTblUsersByUserId() {
        return tblUsersByUserId;
    }

    public void setTblUsersByUserId(UsersEntity tblUsersByUserId) {
        this.tblUsersByUserId = tblUsersByUserId;
    }
}
