package com.springboot.miniecommercewebapp.models;

import javax.persistence.*;
import java.sql.Date;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "tblUsers", schema = "dbo", catalog = "MiniEcommerce")
public class UsersEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "userID", nullable = false)
    private int userId;
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
    private Collection<CartsEntity> tblCartsByUserId;
    @OneToMany(mappedBy = "tblUsersByUserId")
    private Collection<OrdersEntity> tblOrdersByUserId;
    @OneToMany(mappedBy = "tblUsersByUserId")
    private Collection<RatingsEntity> tblRatingsByUserId;
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

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UsersEntity that = (UsersEntity) o;
        return userId == that.userId && roleId == that.roleId && phone == that.phone && status == that.status && Objects.equals(fullName, that.fullName) && Objects.equals(password, that.password) && Objects.equals(address, that.address) && Objects.equals(birthday, that.birthday) && Objects.equals(email, that.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, fullName, password, roleId, address, birthday, phone, email, status);
    }

    public Collection<CartsEntity> getTblCartsByUserId() {
        return tblCartsByUserId;
    }

    public void setTblCartsByUserId(Collection<CartsEntity> tblCartsByUserId) {
        this.tblCartsByUserId = tblCartsByUserId;
    }

    public Collection<OrdersEntity> getTblOrdersByUserId() {
        return tblOrdersByUserId;
    }

    public void setTblOrdersByUserId(Collection<OrdersEntity> tblOrdersByUserId) {
        this.tblOrdersByUserId = tblOrdersByUserId;
    }

    public Collection<RatingsEntity> getTblRatingsByUserId() {
        return tblRatingsByUserId;
    }

    public void setTblRatingsByUserId(Collection<RatingsEntity> tblRatingsByUserId) {
        this.tblRatingsByUserId = tblRatingsByUserId;
    }

    public RolesEntity getTblRolesByRoleId() {
        return tblRolesByRoleId;
    }

    public void setTblRolesByRoleId(RolesEntity tblRolesByRoleId) {
        this.tblRolesByRoleId = tblRolesByRoleId;
    }
}
