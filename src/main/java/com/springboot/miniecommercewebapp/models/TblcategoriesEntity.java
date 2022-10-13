package com.springboot.miniecommercewebapp.models;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "tblcategories", schema = "miniecommerce", catalog = "")
public class TblcategoriesEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "catagoryID", nullable = false)
    private int catagoryId;
    @Basic
    @Column(name = "catagoryName", nullable = false, length = 100)
    private String catagoryName;
    @OneToMany(mappedBy = "tblcategoriesByCatagoryId")
    private Collection<TblproductsEntity> tblproductsByCatagoryId;

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
        TblcategoriesEntity that = (TblcategoriesEntity) o;
        return catagoryId == that.catagoryId && Objects.equals(catagoryName, that.catagoryName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(catagoryId, catagoryName);
    }

    public Collection<TblproductsEntity> getTblproductsByCatagoryId() {
        return tblproductsByCatagoryId;
    }

    public void setTblproductsByCatagoryId(Collection<TblproductsEntity> tblproductsByCatagoryId) {
        this.tblproductsByCatagoryId = tblproductsByCatagoryId;
    }
}
