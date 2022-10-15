package com.springboot.miniecommercewebapp.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.sql.Date;
import java.util.Collection;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
@Table(name = "tblUsers", schema = "dbo", catalog = "MiniEcommerce")
public class Users {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "userID", nullable = false, length = 20)
    private String userId;
    @Basic
    @Column(name = "fullName", nullable = false, length = 25)
    private String fullName;
    @Basic
    @Column(name = "password", nullable = false, length = 25)
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
    @Column(name = "phone", nullable = false, precision = 0)
    private int phone;
    @Basic
    @Column(name = "email", nullable = false, length = 50)
    private String email;
    @Basic
    @Column(name = "status", nullable = false)
    private boolean status;
    @OneToMany(mappedBy = "tblUsersByUserId")
    @JsonIgnore
    private Collection<Carts> tblCartsByUserId;
    @OneToMany(mappedBy = "tblUsersByUserId")
    @JsonIgnore
    private Collection<Orders> tblOrdersByUserId;
    @OneToMany(mappedBy = "tblUsersByUserId")
    @JsonIgnore
    private Collection<Ratings> tblRatingsByUserId;
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "roleID", referencedColumnName = "roleID", nullable = false,
            insertable = false, updatable=false)
    private Roles tblRolesByRoleId;
}
