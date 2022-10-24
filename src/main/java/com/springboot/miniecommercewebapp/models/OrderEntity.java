package com.springboot.miniecommercewebapp.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.Collection;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
@Table(name = "tblOrders", schema = "dbo", catalog = "MiniEcommerce")
public class OrderEntity {
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
    @Column(name = "userID", nullable = false, length = 20)
    private String userId;
    @Basic
    @Column(name = "status", nullable = false)
    private boolean status;
    @OneToMany(mappedBy = "tblOrdersByOrderId")
    @JsonIgnore
    private Collection<OrderDetailEntity> tblOrderDetailsByOrderId;
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "userID", referencedColumnName = "userID", nullable = false,
            insertable = false, updatable=false)
    private UserEntity tblUsersByUserId;
}
