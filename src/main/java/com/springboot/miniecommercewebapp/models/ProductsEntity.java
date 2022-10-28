package com.springboot.miniecommercewebapp.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.springboot.miniecommercewebapp.models.enums.EProductStatus;
import com.springboot.miniecommercewebapp.models.enums.EUserStatus;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.sql.Date;
import java.util.Collection;

@Entity
@Data
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
    @Length(min = 5, max = 100)
    private String description;
    @Basic
    @Column(name = "createDate", nullable = false)
    @NotNull
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
    @NotNull
    //@Enumerated(EnumType.STRING)
    private String status;
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
    @JoinColumn(name = "catagoryID", referencedColumnName = "catagoryID", nullable = false
            , insertable = false, updatable = false)
    @JsonIgnore
    private CategoriesEntity tblCategoriesByCatagoryId;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "tblProductsByProductId")
    @JsonIgnore
    private Collection<RatingsEntity> tblRatingsByProductId;
}
