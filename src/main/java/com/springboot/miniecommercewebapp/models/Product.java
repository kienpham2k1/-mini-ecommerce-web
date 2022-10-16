package com.springboot.miniecommercewebapp.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.Collection;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
@Table(name = "tblProducts", schema = "dbo", catalog = "MiniEcommerce")
public class Product {
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
    @JsonIgnore
    private Collection<Cart> tblCartsByProductId;
    @OneToMany(mappedBy = "tblProductsByProductId")
    @JsonIgnore
    private Collection<OrderDetail> tblOrderDetailsByProductId;
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "catagoryID", referencedColumnName = "catagoryID", nullable = false,
            insertable = false, updatable=false)
    private Categories tblCategoriesByCatagoryId;
    @OneToMany(mappedBy = "tblProductsByProductId")
    @JsonIgnore
    private Collection<Rating> tblRatingsByProductId;
}
