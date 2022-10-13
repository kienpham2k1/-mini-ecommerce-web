package com.springboot.miniecommercewebapp.models;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "tblroles", schema = "miniecommerce", catalog = "")
public class TblrolesEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "roleID", nullable = false)
    private int roleId;
    @Basic
    @Column(name = "roleName", nullable = false, length = 20)
    private String roleName;
    @OneToMany(mappedBy = "tblrolesByRoleId")
    private Collection<TbladminsEntity> tbladminsByRoleId;
    @OneToMany(mappedBy = "tblrolesByRoleId")
    private Collection<TblusersEntity> tblusersByRoleId;

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
        TblrolesEntity that = (TblrolesEntity) o;
        return roleId == that.roleId && Objects.equals(roleName, that.roleName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(roleId, roleName);
    }

    public Collection<TbladminsEntity> getTbladminsByRoleId() {
        return tbladminsByRoleId;
    }

    public void setTbladminsByRoleId(Collection<TbladminsEntity> tbladminsByRoleId) {
        this.tbladminsByRoleId = tbladminsByRoleId;
    }

    public Collection<TblusersEntity> getTblusersByRoleId() {
        return tblusersByRoleId;
    }

    public void setTblusersByRoleId(Collection<TblusersEntity> tblusersByRoleId) {
        this.tblusersByRoleId = tblusersByRoleId;
    }
}
