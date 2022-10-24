package com.springboot.miniecommercewebapp.dto.request;
import lombok.Data;

import java.io.Serializable;

@Data
public class UserLoginRequestModel implements Serializable {
    private static final long serialVersionUID = 2636936156391265891L;
    private String username;
    private String password;
}