package com.springboot.miniecommercewebapp.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.sql.Date;
import java.util.Collection;

@Entity
@Data
@Table(name = "tblProducts", schema = "dbo", catalog = "MiniEcommerce")
public class Product {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "productID", nullable = false)
    private int productId;
    @Basic
    @Column(name = "productName", nullable = false, length = 100, unique = true)
    @NotNull(message = "Name can not be null")
    @Size(min = 5, max = 100)
    private String productName;
    @Basic
    @Column(name = "description", nullable = false, length = 1000)
    @NotNull(message = "Description can not be null")
    private String description;
    @Basic
    @Column(name = "createdDate", nullable = false, unique = true)
    private Date createdDate;
    @Basic
    @Column(name = "image", nullable = false, length = -1)
    private String image;
    @Basic
    @Column(name = "price", nullable = false, precision = 0)
    @Min(value = 1  , message = "Price can not equal 0")
    private double price;
    @Basic
    @Column(name = "quantity", nullable = false )
    @Min(value = 1, message = "Quantity can not be null")
    private int quantity;
    @Basic
    @Column(name = "catagoryID", nullable = false)
    @NotNull(message = "Category can not be null")
    private int catagoryId;
    @Basic
    @Column(name = "status", nullable = false)
    @NotNull(message = "Status can not be null")
    //enum?
    private boolean status;
    @OneToMany(mappedBy = "tblProductsByProductId")
    @JsonIgnore
    private Collection<Cart> tblCartsByProductId;
    @OneToMany(mappedBy = "tblProductsByProductId")
    @JsonIgnore
    private Collection<OrderDetail> tblOrderDetailsByProductId;
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "catagoryID", referencedColumnName = "catagoryID", nullable = false,
            insertable = false, updatable=false)
    private Category tblCategoriesByCatagoryId;
    @OneToMany(mappedBy = "tblProductsByProductId")
    @JsonIgnore
    private Collection<Rating> tblRatingsByProductId;
}
