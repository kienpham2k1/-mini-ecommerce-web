package com.springboot.miniecommercewebapp.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.springboot.miniecommercewebapp.models.enums.EProductStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Date;
import java.util.Collection;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tblProducts", schema = "dbo", catalog = "MiniEcommerce")
public class ProductsEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "productID", nullable = false)
    private int productId;
    @Basic
    @Column(name = "productName", nullable = false, length = 100)
    @NotNull
    @NotBlank
    @Length(min = 5, max = 100)
    private String productName;
    @Basic
    @Column(name = "description", nullable = false, length = 500)
    @NotNull
    @NotBlank
    @Length(min = 5, max = 500)
    private String description;
    @Basic
    @Column(name = "createDate", nullable = false)
    private Date createDate;
    @Basic
    @Column(name = "price", nullable = false, precision = 0)
    @NotNull
    @DecimalMin(value = "0.1")
    private double price;
    @Basic
    @Column(name = "quantity", nullable = false)
    @NotNull
    @Min(value = 0)
    private int quantity;
    @Basic
    @Column(name = "catagoryID", nullable = false)
    @NotNull
    private int catagoryId;
    @Basic
    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private EProductStatus status;
    @Basic
    @Column(name = "imgMain", nullable = false)
    @NotNull
    @NotBlank
    private String imgMain;
    @OneToMany(mappedBy = "tblProductsByProductId")
    @JsonIgnore
    private Collection<CartsEntity> tblCartsByProductId;
    @OneToMany(mappedBy = "tblProductsByProductId")
    @JsonIgnore
    private Collection<ImgEntity> tblImgsByProductId;
    @OneToMany(mappedBy = "tblProductsByProductId")
    @JsonIgnore
    private Collection<OrderItemsEntity> tblOrderItemsByProductId;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "catagoryID", referencedColumnName = "catagoryID", nullable = false, insertable = false, updatable = false)
//    @JsonIgnore
    private CategoriesEntity tblCategoriesByCatagoryId;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "tblProductsByProductId")
    @JsonIgnore
    private Collection<RatingsEntity> tblRatingsByProductId;
    @Transient
    private Integer score;

    public Integer getScore() {
        Integer totalScore = 0;
        if (tblRatingsByProductId == null) return  null;
        if (tblRatingsByProductId.size() > 0) {
            for (RatingsEntity i : tblRatingsByProductId) {
                totalScore += i.getScore();
            }
            return totalScore / tblRatingsByProductId.size();
        }
        return null;
    }
}
