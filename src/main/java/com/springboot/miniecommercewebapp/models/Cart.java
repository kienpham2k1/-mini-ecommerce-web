package com.springboot.miniecommercewebapp.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
@Table(name = "tblCarts", schema = "dbo", catalog = "MiniEcommerce")
public class Cart {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "cartID", nullable = false)
    private int cartId;
    @Basic
    @Column(name = "userID", nullable = false, length = 20)
    @NotNull(message = "User id reference can not be null")
    private String userId;
    @Basic
    @Column(name = "productID", nullable = false)
    @NotNull(message = "Product id reference can not be null")
    private int productId;
    @Basic
    @Column(name = "quantity", nullable = false)
    @Min(value = 1, message = "Quantity can not less than 1")
    private int quantity;
    @Basic
    @Column(name = "price", nullable = true)
//    @Min(value = 1,message = "Price can not be 0")
    private double price;
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "userID", referencedColumnName = "userID", nullable = false,
            insertable = false, updatable=false)
    private User tblUsersByUserId;
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "productID", referencedColumnName = "productID", nullable = false,
            insertable = false, updatable=false)
    private Product tblProductsByProductId;
}
