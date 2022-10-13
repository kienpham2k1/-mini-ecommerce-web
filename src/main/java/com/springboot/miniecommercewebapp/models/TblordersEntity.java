package com.springboot.miniecommercewebapp.models;

import javax.persistence.*;
import java.sql.Date;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "tblorders", schema = "miniecommerce", catalog = "")
public class TblordersEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "orderID", nullable = false)
    private int orderId;
    @Basic
    @Column(name = "orderDate", nullable = false)
    private Date orderDate;
    @Basic
    @Column(name = "total", nullable = false, precision = 0)
    private double total;
    @Basic
    @Column(name = "userID", nullable = false)
    private int userId;
    @Basic
    @Column(name = "status", nullable = false)
    private boolean status;
    @OneToMany(mappedBy = "tblordersByOrderId")
    private Collection<TblorderdetailsEntity> tblorderdetailsByOrderId;
    @ManyToOne
    @JoinColumn(name = "userID", referencedColumnName = "userID", nullable = false)
    private TblusersEntity tblusersByUserId;

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TblordersEntity that = (TblordersEntity) o;
        return orderId == that.orderId && Double.compare(that.total, total) == 0 && userId == that.userId && status == that.status && Objects.equals(orderDate, that.orderDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId, orderDate, total, userId, status);
    }

    public Collection<TblorderdetailsEntity> getTblorderdetailsByOrderId() {
        return tblorderdetailsByOrderId;
    }

    public void setTblorderdetailsByOrderId(Collection<TblorderdetailsEntity> tblorderdetailsByOrderId) {
        this.tblorderdetailsByOrderId = tblorderdetailsByOrderId;
    }

    public TblusersEntity getTblusersByUserId() {
        return tblusersByUserId;
    }

    public void setTblusersByUserId(TblusersEntity tblusersByUserId) {
        this.tblusersByUserId = tblusersByUserId;
    }
}
