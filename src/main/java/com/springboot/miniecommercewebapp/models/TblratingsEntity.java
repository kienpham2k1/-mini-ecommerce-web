package com.springboot.miniecommercewebapp.models;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "tblratings", schema = "miniecommerce", catalog = "")
public class TblratingsEntity {
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
    @JoinColumn(name = "productID", referencedColumnName = "productID", nullable = false)
    private TblproductsEntity tblproductsByProductId;
    @ManyToOne
    @JoinColumn(name = "userID", referencedColumnName = "userID", nullable = false)
    private TblusersEntity tblusersByUserId;

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
        TblratingsEntity that = (TblratingsEntity) o;
        return ratingId == that.ratingId && productId == that.productId && userId == that.userId && score == that.score;
    }

    @Override
    public int hashCode() {
        return Objects.hash(ratingId, productId, userId, score);
    }

    public TblproductsEntity getTblproductsByProductId() {
        return tblproductsByProductId;
    }

    public void setTblproductsByProductId(TblproductsEntity tblproductsByProductId) {
        this.tblproductsByProductId = tblproductsByProductId;
    }

    public TblusersEntity getTblusersByUserId() {
        return tblusersByUserId;
    }

    public void setTblusersByUserId(TblusersEntity tblusersByUserId) {
        this.tblusersByUserId = tblusersByUserId;
    }
}
