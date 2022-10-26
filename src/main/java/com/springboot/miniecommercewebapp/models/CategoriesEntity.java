package com.springboot.miniecommercewebapp.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Data
@Table(name = "tblCategories", schema = "dbo", catalog = "MiniEcommerce")
public class CategoriesEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "catagoryID", nullable = false)
    private int catagoryId;
    @Basic
    @Column(name = "catagoryName", nullable = false, length = 100)
    private String catagoryName;
    @OneToMany(mappedBy = "tblCategoriesByCatagoryId")
    @JsonIgnore
    private Collection<ProductsEntity> tblProductsByCatagoryId;
}
