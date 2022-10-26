package com.springboot.miniecommercewebapp.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
//    @NotNull(message = "Product name must not null")
//    @NotBlank(message = "Product name must not blank")
    //@Length(min = 5, max = 100, message = "Name length is [5, 100]")
    private String productName;
    @Basic
    @Column(name = "description", nullable = false, length = 500)
//    @NotNull(message = "Description must not null")
//    @NotBlank(message = "Description must not blank")
    //@Length(min = 5, max = 100, message = "Description length is [5, 1000]")
    private String description;
    @Basic
    @Column(name = "createDate", nullable = false)
//    @NotNull(message = "Create date must not null")
    private Date createDate;
    @Basic
    @Column(name = "price", nullable = false, precision = 0)
//    @NotNull(message = "Price must not null")
//    @DecimalMin(value = "0.1", message = "Price must greater than 0")
    private double price;
    @Basic
    @Column(name = "quantity", nullable = false)
//    @NotNull(message = "Quantity must not null")
    @Min(value = 1, message = "Quantity must greater than 0")
    private int quantity;
    @Basic
    @Column(name = "catagoryID", nullable = false)
    //@NotNull(message = "Catagory ID must not null")
    private int catagoryId;
    @Basic
    @Column(name = "status", nullable = false)
//    @NotNull(message = "Status must not null")
    private int status;
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
