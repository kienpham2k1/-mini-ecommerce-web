package com.springboot.miniecommercewebapp.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.Collection;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
@Table(name = "tblRoles", schema = "dbo", catalog = "MiniEcommerce")
public class RoleEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "roleID", nullable = false)
    private int roleId;
    @Basic
    @Column(name = "roleName", nullable = false, length = 20)
    private String roleName;
    @OneToMany(mappedBy = "tblRolesByRoleId")
    @JsonIgnore
    private Collection<AdminEntity> tblAdminsByRoleId;
    @OneToMany(mappedBy = "tblRolesByRoleId")
    @JsonIgnore
    private Collection<UserEntity> tblUsersByRoleId;
}
