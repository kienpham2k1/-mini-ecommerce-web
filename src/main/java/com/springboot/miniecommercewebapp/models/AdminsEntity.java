package com.springboot.miniecommercewebapp.models;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "tblAdmins", schema = "dbo", catalog = "MiniEcommerce")
public class AdminsEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "userID", nullable = false)
    private int userId;
    @Basic
    @Column(name = "password", nullable = false, length = 25)
    private String password;
    @Basic
    @Column(name = "roleID", nullable = false)
    private int roleId;
    @ManyToOne
    @JoinColumn(name = "roleID", referencedColumnName = "roleID", nullable = false,
            insertable = false, updatable=false)
    private RolesEntity tblRolesByRoleId;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AdminsEntity that = (AdminsEntity) o;
        return userId == that.userId && roleId == that.roleId && Objects.equals(password, that.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, password, roleId);
    }

    public RolesEntity getTblRolesByRoleId() {
        return tblRolesByRoleId;
    }

    public void setTblRolesByRoleId(RolesEntity tblRolesByRoleId) {
        this.tblRolesByRoleId = tblRolesByRoleId;
    }
}
