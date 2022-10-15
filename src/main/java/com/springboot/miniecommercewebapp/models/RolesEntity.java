package com.springboot.miniecommercewebapp.models;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
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
    private Collection<AdminsEntity> tblAdminsByRoleId;
    @OneToMany(mappedBy = "tblRolesByRoleId")
    private Collection<UsersEntity> tblUsersByRoleId;

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RolesEntity that = (RolesEntity) o;
        return roleId == that.roleId && Objects.equals(roleName, that.roleName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(roleId, roleName);
    }

    public Collection<AdminsEntity> getTblAdminsByRoleId() {
        return tblAdminsByRoleId;
    }

    public void setTblAdminsByRoleId(Collection<AdminsEntity> tblAdminsByRoleId) {
        this.tblAdminsByRoleId = tblAdminsByRoleId;
    }

    public Collection<UsersEntity> getTblUsersByRoleId() {
        return tblUsersByRoleId;
    }

    public void setTblUsersByRoleId(Collection<UsersEntity> tblUsersByRoleId) {
        this.tblUsersByRoleId = tblUsersByRoleId;
    }
}
