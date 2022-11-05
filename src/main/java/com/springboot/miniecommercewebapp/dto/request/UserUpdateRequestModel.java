package com.springboot.miniecommercewebapp.dto.request;

import com.springboot.miniecommercewebapp.models.enums.EUserStatus;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;
import java.sql.Date;

@Getter
@Setter
@Data
public class UserUpdateRequestModel {
    //    private String userId;
    @NotNull
    @Length(min = 5)
    private String fullName;
    @NotNull
    @Length(min = 5)
    private String password;
    @NotNull
    @Length(min = 5)
    private String address;
    @NotNull
    Date birthday;
    @NotNull
    @Length(min = 5)
    private String phone;
    @NotNull
    @Length(min = 5)
    private String email;
    @NotNull
    @Enumerated(EnumType.STRING)
    EUserStatus status;
    int roleId;
}
