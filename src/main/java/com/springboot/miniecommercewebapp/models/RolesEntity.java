package com.springboot.miniecommercewebapp.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
@Data
@Table(name = "tblRoles", schema = "dbo", catalog = "MiniEcommerce")
public class RolesEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "roleID", nullable = false)
    private int roleId;
    @Basic
    @Column(name = "roleName", nullable = false, length = 20)
    private String roleName;
    @OneToMany(mappedBy = "tblRolesByRoleId")
    @JsonIgnore
    private Collection<AdminsEntity> tblAdminsByRoleId;
    @OneToMany(mappedBy = "tblRolesByRoleId")
    @JsonIgnore
    private Collection<UsersEntity> tblUsersByRoleId;
}
