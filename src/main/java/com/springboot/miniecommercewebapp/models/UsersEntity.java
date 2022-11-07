package com.springboot.miniecommercewebapp.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.springboot.miniecommercewebapp.models.enums.ERoleName;
import com.springboot.miniecommercewebapp.models.enums.EUserStatus;
import lombok.*;

import javax.persistence.*;
import java.sql.Date;
import java.util.Collection;
import java.util.Objects;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tblUsers", schema = "dbo", catalog = "MiniEcommerce")
public class UsersEntity {
    @Id
    @Column(name = "userID", nullable = false, length = 20)
    private String userId;
    @Basic
    @Column(name = "fullName", nullable = false, length = 25)
    private String fullName;
    @Basic
    @JsonIgnore
    @Column(name = "password", nullable = false, length = 150)
    private String password;
    @Basic
    @Column(name = "roleID", nullable = false)
    private int roleId;
    @Basic
    @Column(name = "address", nullable = false, length = 250)
    private String address;
    @Basic
    @Column(name = "birthday", nullable = true)
    private Date birthday;
    @Basic
    @Column(name = "phone", nullable = false, length = 11)
    private String phone;
    @Basic
    @Column(name = "email", nullable = false, length = 50)
    private String email;
    @Basic
    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private EUserStatus status;
    @OneToMany(mappedBy = "tblUsersByUserId")
    @JsonIgnore
    private Collection<CartsEntity> tblCartsByUserId;
    @OneToMany(mappedBy = "tblUsersByUserId")
    @JsonIgnore
    private Collection<OrdersEntity> tblOrdersByUserId;
    @OneToMany(mappedBy = "tblUsersByUserId")
    @JsonIgnore
    private Collection<RatingsEntity> tblRatingsByUserId;
    @ManyToOne
    @JoinColumn(name = "roleID", referencedColumnName = "roleID", nullable = false, insertable = false, updatable = false)
    private RolesEntity tblRolesByRoleId;
}