package com.springboot.miniecommercewebapp.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "tblAdmins", schema = "dbo", catalog = "MiniEcommerce")
public class AdminsEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "userID", nullable = false, length = 20)
    private String userId;
    @Basic
    @Column(name = "password", nullable = false, length = 25)
    private String password;
    @Basic
    @Column(name = "roleID", nullable = false)
    private int roleId;
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "roleID", referencedColumnName = "roleID", nullable = false
            , insertable = false, updatable = false)
    private RolesEntity tblRolesByRoleId;
}
