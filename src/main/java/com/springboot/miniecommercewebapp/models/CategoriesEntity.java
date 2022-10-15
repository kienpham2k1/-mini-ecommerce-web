package com.springboot.miniecommercewebapp.models;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
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
    private Collection<ProductsEntity> tblProductsByCatagoryId;

    public int getCatagoryId() {
        return catagoryId;
    }

    public void setCatagoryId(int catagoryId) {
        this.catagoryId = catagoryId;
    }

    public String getCatagoryName() {
        return catagoryName;
    }

    public void setCatagoryName(String catagoryName) {
        this.catagoryName = catagoryName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CategoriesEntity that = (CategoriesEntity) o;
        return catagoryId == that.catagoryId && Objects.equals(catagoryName, that.catagoryName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(catagoryId, catagoryName);
    }

    public Collection<ProductsEntity> getTblProductsByCatagoryId() {
        return tblProductsByCatagoryId;
    }

    public void setTblProductsByCatagoryId(Collection<ProductsEntity> tblProductsByCatagoryId) {
        this.tblProductsByCatagoryId = tblProductsByCatagoryId;
    }
}
