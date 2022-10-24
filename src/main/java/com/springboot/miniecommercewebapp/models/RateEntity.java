package com.springboot.miniecommercewebapp.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
@Table(name = "tblRatings", schema = "dbo", catalog = "MiniEcommerce")
public class RateEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "ratingID", nullable = false)
    private int ratingId;
    @Basic
    @Column(name = "productID", nullable = false)
    @NotNull(message = "Product id reference can not be null")
    private int productId;
    @Basic
    @Column(name = "userID", nullable = false, length = 20)
    @NotNull(message = "User id reference can not be null")
    private String userId;
    @Basic
    @Column(name = "score", nullable = false)
    @NotNull(message = "Score can not be null")
    @Min(value = 0, message = "Score can not be negative")
    @Max(value = 10, message = "Score can not be greater than 10")
    private int score;
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "productID", referencedColumnName = "productID", nullable = false,
            insertable = false, updatable=false)
    private ProductEntity tblProductsByProductId;
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "userID", referencedColumnName = "userID", nullable = false,
            insertable = false, updatable=false)
    private UserEntity tblUsersByUserId;
}
