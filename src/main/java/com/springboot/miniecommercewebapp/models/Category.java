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
@Table(name = "tblCategories", schema = "dbo", catalog = "MiniEcommerce")
public class Category {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "catagoryID", nullable = false)
    private int catagoryId;
    @Basic
    @Column(name = "catagoryName", nullable = false, length = 100)
    private String catagoryName;
    @Basic
    @Column(name = "description", nullable = true, length = 1000)
    private String description;
    @OneToMany(mappedBy = "tblCategoriesByCatagoryId")
    @JsonIgnore
    private Collection<Product> tblProductsByCatagoryId;
}
