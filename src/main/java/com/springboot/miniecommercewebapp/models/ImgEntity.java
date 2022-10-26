package com.springboot.miniecommercewebapp.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Data
@Table(name = "tblImg", schema = "dbo", catalog = "MiniEcommerce")
public class ImgEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "imgId", nullable = false)
    private int imgId;
    @Basic
    @Column(name = "productID", nullable = false)
    private int productId;
    @Basic
    @Column(name = "image", nullable = false, length = -1)
    private String image;
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "productID", referencedColumnName = "productID", nullable = false, insertable = false, updatable = false)
    private ProductsEntity tblProductsByProductId;
}
